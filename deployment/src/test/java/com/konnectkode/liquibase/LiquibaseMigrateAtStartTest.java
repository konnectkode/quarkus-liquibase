package com.konnectkode.liquibase;

import io.quarkus.test.QuarkusUnitTest;
import liquibase.Liquibase;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import javax.inject.Inject;

public class LiquibaseMigrateAtStartTest {

    @Inject
    Liquibase liquibase;

    @RegisterExtension
    static final QuarkusUnitTest config = new QuarkusUnitTest()
            .setArchiveProducer(() -> ShrinkWrap.create(JavaArchive.class)
                    .addAsResource("migrate-at-start.properties", "application.properties"));

    @Test
    @DisplayName("Migrates at start correctly")
    public void liquibaseMigrateAtStart() throws Exception {
        Assertions.assertNotNull(liquibase);

        liquibase.getDatabaseChangeLog().validate(liquibase.getDatabase());
    }

}
