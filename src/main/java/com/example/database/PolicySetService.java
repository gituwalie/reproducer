package database;


import com.netcracker.rmi.ac.pojo.PolicySetWrapper;
import com.netcracker.rmi.ac.service.PolicySetConverter;
import com.netcracker.security.authorization.abac.api.engine.model.policy.PolicySet;
import com.netcracker.security.authorization.abac.api.rest.context.RestContext;
import com.netcracker.security.authorization.abac.api.rest.pap.extension.repository.AbstractPolicySetRepository;
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
public class PolicySetService extends AbstractPolicySetRepository {
    @Inject
    EntityManagerProvider emp;
    @Inject
    PolicySetConverter converter;

    @Override
    public @NotNull Collection<PolicySet> findAll() {
        return emp.getEntityManager().createQuery("FROM PolicySetWrapper", PolicySetWrapper.class)
                .getResultList()
                .stream()
                .map(converter::decode)
                .collect(Collectors.toSet());
    }

    @Override
    @Transactional
    public void saveAll(RestContext ctx, @NotNull Collection<PolicySet> policySets) {
        for (PolicySet pip : policySets) {
            boolean exists = exists(pip.getId().toString(), pip.getTenantId());
            if (!exists) {
                PolicySetWrapper q = converter.encode(pip);
                q.setTenantId(ctx.getTenant());
                emp.getEntityManager().persist(q);
            }
        }
        emp.getEntityManager().flush();
    }

    @Override
    @Transactional
    public void deleteByIdIn(RestContext ctx, @NotNull Collection<UUID> ids) {
        List<PolicySetWrapper> resultList = emp.getEntityManager().createQuery("SELECT e from PolicySetWrapper e where e.tenantId = :tenant_id and e.id IN (:to_del)", PolicySetWrapper.class)
                .setParameter("tenant_id", ctx.getTenant())
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
