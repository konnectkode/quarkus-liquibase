package com.konnectkode.liquibase.runtime;

import io.agroal.api.AgroalDataSource;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseConnection;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.DatabaseException;
import liquibase.resource.ClassLoaderResourceAccessor;
import liquibase.resource.ResourceAccessor;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import java.sql.SQLException;

@ApplicationScoped
public class LiquibaseProducer {

    @Inject
    AgroalDataSource dataSource;

    private LiquibaseBuildConfig liquibaseBuildConfig;

    @Produces
    @Dependent
    public Liquibase produceLiquibase() {
        ResourceAccessor resourceAccessor = new ClassLoaderResourceAccessor(Thread.currentThread().getContextClassLoader());

        try {
            DatabaseConnection databaseConnection = new JdbcConnection(dataSource.getConnection());
            Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(databaseConnection);

            if (liquibaseBuildConfig.defaultSchemaName.isPresent()) {
                database.setDefaultSchemaName(liquibaseBuildConfig.defaultSchemaName.get());
            }

            return new Liquibase(liquibaseBuildConfig.changelog, resourceAccessor, database);
        } catch (SQLException | DatabaseException e) {
            throw new RuntimeException(e);
        }
    }

    public void setLiquibaseBuildConfig(LiquibaseBuildConfig liquibaseBuildConfig) {
        this.liquibaseBuildConfig = liquibaseBuildConfig;
    }

}
