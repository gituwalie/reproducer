package com.example.service;


import java.io.File;
import java.util.List;
import com.example.pojo.Package;

public interface PackageProcessionService {
    void processPackage(File zip, String tenant);

    List<Package> listPackages(String tenant);

    void deletePackage(Long id);

    void deleteBulkByTenant(String tenantId);

    Package getById(Long id);
}
