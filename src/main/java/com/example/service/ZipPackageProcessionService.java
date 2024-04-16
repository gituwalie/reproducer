package com.example.service;

import com.example.ApplicationException;
import com.example.ExceptionType;
import com.example.database.PackageService;
import com.example.pojo.Package;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.netty.handler.codec.http.HttpResponseStatus;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.io.File;
import java.util.List;
import java.util.Objects;

@ApplicationScoped
public class ZipPackageProcessionService implements PackageProcessionService {

    @Inject
    PackageService packageService;
    @Inject
    ZipDataService zipDataService;
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(ZipPackageProcessionService.class);


    @Override
    public void processPackage(File zip, String tenant) {
        Package pkg = new Package();


        try {
            ZipData zipData = zipDataService.processZip(zip);


        } catch (ApplicationException e) {
            if (log.isErrorEnabled()) {
                log.error("Sources cannot be processed because {}", e.getMessage());
            }
            throw e;
        } catch (JsonProcessingException e) {
            if (log.isErrorEnabled()) {
                log.error("Sources cannot be processed because {}", e.getMessage());
            }
            throw new ApplicationException(e, ExceptionType.DATA, "Json processing failed", 400);
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("Package cannot be processed because {}", e.getMessage());
            }
            throw new ApplicationException(e, ExceptionType.DATA, "File data extraction failed", 400);
        }


        boolean exists = packageService.exists(pkg.getPackageName(), pkg.getVersion());
        if (!exists) {
            packageService.create(pkg);
        }
    }


    @Override
    public List<Package> listPackages(String tenant) {
        if (Objects.isNull(tenant) || tenant.isEmpty()){
            return packageService.readALL();
        }
        return packageService.read(tenant);
    }

    @Override
    public void deletePackage(Long id) {
        packageService.delete(id);
    }

    @Override
    public void deleteBulkByTenant(String tenantId) {
        packageService.delete(tenantId);
    }

    @Override
    public Package getById(Long id) {
        return packageService.read(id);
    }
}
