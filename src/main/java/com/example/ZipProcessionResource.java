package com.example;


import com.example.service.PackageProcessionService;
import io.netty.handler.codec.http.HttpResponseStatus;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Response;

import java.io.File;
import java.util.List;
import java.util.Objects;

@ApplicationScoped
@Path("/rmi/api/zip")
public class ZipProcessionResource {
    @Inject
    PackageProcessionService packageProcessionService;

    @POST
    @Consumes("application/zip")
    public Response upload(File zip,
                           @QueryParam("tenant_id") String tenant) {
        if (Objects.isNull(tenant) || tenant.isEmpty()) {
            throw new ApplicationException(ExceptionType.DATA, "No tenant_id provided", HttpResponseStatus.BAD_GATEWAY.code());
        }
        packageProcessionService.processPackage(zip, tenant);
        return Response.ok().build();
    }

    @GET
    @Produces("application/json")
    public Response listPackages(@QueryParam("tenant_id") String tenant) {
        List<com.example.pojo.Package> packages = packageProcessionService.listPackages(tenant);
        return Response.ok(packages).build();
    }

    @DELETE
    @Path("/{id}")
    @Produces("application/json")
    public Response deleteById(@PathParam("id") Long id) {
        packageProcessionService.deletePackage(id);
        return Response.noContent().build();
    }

    @GET
    @Path("/{id}")
    @Produces("application/json")
    public Response getById(@PathParam("id") Long id) {
        com.example.pojo.Package  byId = packageProcessionService.getById(id);
        if (Objects.isNull(byId)){
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(byId).build();
    }

    @DELETE
    @Produces("application/json")
    public Response deleteBulkByTenant(@QueryParam("tenant_id") String tenant) {
        packageProcessionService.deleteBulkByTenant(tenant);
        return Response.noContent().build();
    }
}
