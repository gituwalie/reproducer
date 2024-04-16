package com.example.database;


import com.example.pojo.PolicyInformationPointWrapper;
import jakarta.annotation.Priority;
import jakarta.enterprise.inject.Alternative;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;

import java.util.Collection;

@Singleton
@Alternative
@Priority(999)
public class PolicyInformationPointService {
    @Inject
    EntityManagerProvider emp;

    private boolean exists(String id, String tenantId) {
        String query = "select COUNT(e.id) from PolicyInformationPointWrapper e where e.id = :id and tenantId = :tenant_id";
        Long count = emp.getEntityManager().createQuery(query, Long.class)
                .setParameter("id", id)
                .setParameter("tenant_id", tenantId)
                .getSingleResult();
        return count > (0L);
    }


    @Transactional
    public void deleteByNameIn(@NotNull Collection<String> names) {
        emp.getEntityManager().createQuery("Delete from PolicyInformationPointWrapper e where e.tenantId = :tenant_id and e.id IN (:ids)", PolicyInformationPointWrapper.class)
                .setParameter("tenant_id", "tenant")
                .setParameter("ids", names)
                .executeUpdate();
    }
}
