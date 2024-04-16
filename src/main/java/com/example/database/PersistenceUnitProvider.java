package com.example.database;

import io.agroal.api.AgroalDataSource;
import io.quarkus.agroal.DataSource;
import io.quarkus.datasource.runtime.DataSourceSupport;
import io.quarkus.hibernate.orm.PersistenceUnit;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.ws.rs.Produces;
import org.hibernate.Session;

import static com.example.Consts.ENTITY_MANAGER_NAME;
import static com.example.Consts.ORACLE_DATASOURCE_NAME;
import static com.example.Consts.POSTGRESQL_DATASOURCE_NAME;

public class PersistenceUnitProvider {
    @Inject
    DataSourceSupport dataSourceSupport;

    @Inject
    @DataSource(POSTGRESQL_DATASOURCE_NAME)
    AgroalDataSource postgresDS;
    @Inject
    @PersistenceUnit(POSTGRESQL_DATASOURCE_NAME)
    Session postgres;

    @Inject
    @DataSource(ORACLE_DATASOURCE_NAME)
    AgroalDataSource oracleDS;
    @Inject
    @PersistenceUnit(ORACLE_DATASOURCE_NAME)
    Session oracle;

    @Produces
    @ApplicationScoped
    public AgroalDataSource dataSource() {
        if (dataSourceSupport.getInactiveNames().contains(POSTGRESQL_DATASOURCE_NAME)) {
            return oracleDS;
        } else {
            return postgresDS;
        }
    }

    @Produces
    @ApplicationScoped
    @Named(ENTITY_MANAGER_NAME)
    public Session session() {
        if (dataSourceSupport.getInactiveNames().contains(POSTGRESQL_DATASOURCE_NAME)) {
            return oracle;
        } else {
            return postgres;
        }
    }
}