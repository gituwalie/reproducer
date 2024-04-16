package com.example.database;


import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.List;

@ApplicationScoped
public class PackageService {
    @Inject
    EntityManagerProvider emp;


    @Transactional
    public com.example.pojo.Package create(com.example.pojo.Package pkg) {
        emp.getEntityManager().persist(pkg);
        return pkg;
    }

    public com.example.pojo.Package read(Long id) {
        if (exists(id)) {
            return emp.getEntityManager().createQuery("FROM Package u WHERE u.id = :id", com.example.pojo.Package.class)
                    .setParameter("id", id)
                    .getSingleResult();
        }
        return null;
    }

    @Transactional
    public void delete(Long id) {
        if (exists(id)) {
            Package pkg = emp.getEntityManager().find(Package.class, id);
            emp.getEntityManager().remove(pkg);
        }
    }

    @Transactional
    public void delete(String tenantId) {
        com.example.pojo.Package p = emp.getEntityManager().createQuery("FROM Package WHERE tenantId = :tenant_id", com.example.pojo.Package.class).setParameter("tenant_id", tenantId).getSingleResult();
        emp.getEntityManager().remove(p);
    }

    public List<com.example.pojo.Package> read(String tenantId) {
        return emp.getEntityManager().createQuery("FROM Package u WHERE u.tenantId = :tenant_id", com.example.pojo.Package.class).setParameter("tenant_id", tenantId).getResultList();
    }

    public List<com.example.pojo.Package> readALL() {
        return emp.getEntityManager().createQuery("FROM Package u", com.example.pojo.Package.class).getResultList();
    }

    public boolean exists(String packageName, String version) {
        Long b = emp.getEntityManager().createQuery("select count(u) FROM Package u WHERE u.packageName = :package_name and u.version = :version", Long.class).setParameter("package_name", packageName).setParameter("version", version).getSingleResult();
        return b > 0;
    }

    public boolean exists(Long id) {
        Long b = emp.getEntityManager().createQuery("select count(u) FROM Package u WHERE u.id = :id", Long.class).setParameter("id", id).getSingleResult();
        return b > 0;
    }
}
