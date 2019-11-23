package com.konnectkode.liquibase;

import io.quarkus.test.QuarkusUnitTest;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

public class FailAtStartTest {

    @RegisterExtension
    static QuarkusUnitTest config = new QuarkusUnitTest()
            .setExpectedException(RuntimeException.class)
            .setArchiveProducer(() -> ShrinkWrap.create(JavaArchive.class)
                .addAsResource("fail-at-start.properties", "application.properties")
            );

    @Test
    @DisplayName("Fails at start without changelog")
    public void failWithoutChangelog() {
        // should not be called, deployment exception should happen first:
        // it's illegal to have migrate-at-start=true without specify changelog file.
        Assertions.fail();
    }

}
