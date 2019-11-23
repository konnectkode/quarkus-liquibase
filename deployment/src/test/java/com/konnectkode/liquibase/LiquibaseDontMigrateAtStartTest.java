package com.konnectkode.liquibase;

import io.agroal.api.AgroalDataSource;
import io.quarkus.test.QuarkusUnitTest;
import liquibase.Liquibase;
import org.h2.jdbc.JdbcSQLException;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import javax.inject.Inject;

public class LiquibaseDontMigrateAtStartTest {

    @Inject
    Liquibase liquibase;

    @Inject
    AgroalDataSource dataSource;

    @RegisterExtension
    static final QuarkusUnitTest config = new QuarkusUnitTest()
            .setArchiveProducer(() -> ShrinkWrap.create(JavaArchive.class)
                    .addAsResource("dont-migrate-at-start.properties", "application.properties"));

    @Test
    @DisplayName("Don't migrates at start")
    public void liquibaseMigrateAtStart() throws Exception {
        liquibase.getDatabaseChangeLog().validate(liquibase.getDatabase());

        Assertions.assertThrows(JdbcSQLException.class, () ->
                dataSource.getConnection().prepareStatement("select 1 from DUMMY_DONT_MIGRATE"));
    }

}
