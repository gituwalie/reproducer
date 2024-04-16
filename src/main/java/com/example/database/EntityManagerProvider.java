package com.example.database;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;

import static com.example.Consts.ENTITY_MANAGER_NAME;

/*
 Simple wrap for entity manager to avoid copying same stuff for each service
 */
@ApplicationScoped
public class EntityManagerProvider {

    @Inject
    @Named(ENTITY_MANAGER_NAME)
    EntityManager em;

    public EntityManagerProvider() {
    }

    public EntityManager getEntityManager() {
        return em;
    }
}
