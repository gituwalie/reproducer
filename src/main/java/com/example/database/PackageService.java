package database;


import com.netcracker.rmi.ac.service.PolicySetConverter;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.List;

@ApplicationScoped
public class PackageService {
    @Inject
    EntityManagerProvider emp;
    @Inject
    PolicySetConverter converter;

    @Transactional
    public Package create(Package pkg) {
        pkg.getPolicySets().forEach(d -> {
            String s = converter.supplyUUIDs(d.getData());
            d.setData(s);
        });
        emp.getEntityManager().persist(pkg);
        return pkg;
    }

    public Package read(Long id) {
        if (exists(id)) {
            return emp.getEntityManager().createQuery("FROM Package u WHERE u.id = :id", Package.class)
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
        Package p = emp.getEntityManager().createQuery("FROM Package WHERE tenantId = :tenant_id", Package.class).setParameter("tenant_id", tenantId).getSingleResult();
        emp.getEntityManager().remove(p);
    }

    public List<Package> read(String tenantId) {
        return emp.getEntityManager().createQuery("FROM Package u WHERE u.tenantId = :tenant_id", Package.class).setParameter("tenant_id", tenantId).getResultList();
    }

    public List<Package> readALL() {
        return emp.getEntityManager().createQuery("FROM Package u", Package.class).getResultList();
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
