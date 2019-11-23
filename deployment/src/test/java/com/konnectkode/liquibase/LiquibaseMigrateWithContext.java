package com.konnectkode.liquibase;

import io.quarkus.test.QuarkusUnitTest;
import liquibase.Liquibase;
import liquibase.exception.LiquibaseException;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import javax.inject.Inject;

public class LiquibaseMigrateWithContext {

    @Inject
    Liquibase liquibase;

    @ConfigProperty(name = "quarkus.liquibase.context")
    String context;

    @RegisterExtension
    static final QuarkusUnitTest config = new QuarkusUnitTest()
            .setArchiveProducer(() -> ShrinkWrap.create(JavaArchive.class)
                    .addAsResource("context.properties", "application.properties"));

    @Test
    @DisplayName("Migrates only the correct context")
    public void liquibaseMigrateCorrectContext() throws Exception {
        Assertions.assertNotNull(liquibase);

        liquibase.getDatabaseChangeLog().validate(liquibase.getDatabase(), context);

        Assertions.assertThrows(LiquibaseException.class, () ->
                liquibase.getDatabaseChangeLog().validate(liquibase.getDatabase(), "production"));
    }
}
