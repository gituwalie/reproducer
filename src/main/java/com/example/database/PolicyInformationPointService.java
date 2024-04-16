package database;


import com.netcracker.rmi.ac.pojo.PolicyInformationPointWrapper;
import com.netcracker.rmi.ac.service.PIPConverter;
import com.netcracker.security.authorization.abac.api.engine.model.pip.PolicyInformationPoint;
import com.netcracker.security.authorization.abac.api.rest.context.RestContext;
import com.netcracker.security.authorization.abac.api.rest.pap.extension.repository.AbstractPolicyInformationPointRepository;
import jakarta.annotation.Priority;
import jakarta.enterprise.inject.Alternative;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;

import java.util.Collection;
import java.util.stream.Collectors;

@Singleton
@Alternative
@Priority(999)
public class PolicyInformationPointService extends AbstractPolicyInformationPointRepository {
    @Inject
    EntityManagerProvider emp;
    @Inject
    PIPConverter converter;

    @Override
    public @NotNull Collection<PolicyInformationPoint> findAll() {
        return emp.getEntityManager().createQuery("FROM PolicyInformationPointWrapper", PolicyInformationPointWrapper.class)
                .getResultList()
                .stream()
                .map(q -> converter.decode(q))
                .collect(Collectors.toSet());
    }

    @Override
    @Transactional
    public void saveAll(RestContext data, @NotNull Collection<PolicyInformationPoint> pips) {
        for (PolicyInformationPoint pip : pips) {
            PolicyInformationPointWrapper q = converter.encode(pip);
            q.setTenantId(data.getTenant());
            boolean exists = exists(pip.getName(), pip.getTenantId());
            if (!exists) {
                emp.getEntityManager().persist(q);
            }
        }
        emp.getEntityManager().flush();
    }

    private boolean exists(String id, String tenantId) {
        String query = "select COUNT(e.id) from PolicyInformationPointWrapper e where e.id = :id and tenantId = :tenant_id";
        Long count = emp.getEntityManager().createQuery(query, Long.class)
                .setParameter("id", id)
                .setParameter("tenant_id", tenantId)
                .getSingleResult();
        return count > (0L);
    }

    @Override
    @Transactional
    public void deleteByNameIn(RestContext data, @NotNull Collection<String> names) {
        emp.getEntityManager().createQuery("Delete from PolicyInformationPointWrapper e where e.tenantId = :tenant_id and e.id IN (:ids)", PolicyInformationPointWrapper.class)
                .setParameter("tenant_id", data.getTenant())
                .setParameter("ids", names)
                .executeUpdate();
    }
}
