package com.example.database;


import com.example.pojo.PolicySetWrapper;
import jakarta.annotation.Priority;
import jakarta.enterprise.inject.Alternative;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;

import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Singleton
@Alternative
@Priority(999)
public class PolicySetService  {
    @Inject
    EntityManagerProvider emp;

    @Transactional
    public void deleteByIdIn(@NotNull Collection<UUID> ids) {
        List<PolicySetWrapper> resultList = emp.getEntityManager().createQuery("SELECT e from PolicySetWrapper e where e.tenantId = :tenant_id and e.id IN (:to_del)", PolicySetWrapper.class)
                .setParameter("tenant_id", "q")
                .setParameter("to_del", ids.stream().map(UUID::toString).collect(Collectors.toSet()))
                .getResultList();
        resultList.forEach(d -> emp.getEntityManager().remove(d));
    }

    private boolean exists(String id, String tenantId) {
        String query = "select COUNT(e.id) from PolicySetWrapper e where e.id = :id and tenantId = :tenant_id";
        Long count = emp.getEntityManager().createQuery(query, Long.class)
                .setParameter("id", id)
                .setParameter("tenant_id", tenantId)
                .getSingleResult();
        return count > (0L);
    }


}
