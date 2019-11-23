package com.konnectkode.liquibase;

import io.agroal.api.AgroalDataSource;
import io.quarkus.test.QuarkusUnitTest;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.h2.jdbc.JdbcSQLException;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import javax.inject.Inject;

public class LiquibaseMigrateWithSchemaTest {

    @Inject
    AgroalDataSource agroalDataSource;

    @ConfigProperty(name = "quarkus.liquibase.default-schema")
    String defaultSchema;

    @RegisterExtension
    static final QuarkusUnitTest config = new QuarkusUnitTest()
            .setArchiveProducer(() -> ShrinkWrap.create(JavaArchive.class)
                    .addAsResource("migrate-with-schema.properties", "application.properties"));

    @Test
    @DisplayName("Migrate for the correct schema")
    public void migrateWithSchemaTest() throws Exception {
        agroalDataSource.getConnection().prepareStatement(String.format("select ID from %s.DUMMY_SCHEMA", defaultSchema));

        Assertions.assertThrows(JdbcSQLException.class, () ->
                agroalDataSource.getConnection().prepareStatement("select ID from PUBLIC.DUMMY_SCHEMA"));
    }

}
