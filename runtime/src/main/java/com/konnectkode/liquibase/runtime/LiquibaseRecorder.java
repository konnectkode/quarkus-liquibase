package com.konnectkode.liquibase.runtime;

import io.quarkus.arc.runtime.BeanContainer;
import io.quarkus.arc.runtime.BeanContainerListener;
import io.quarkus.runtime.annotations.Recorder;
import liquibase.Liquibase;
import liquibase.exception.LiquibaseException;

@Recorder
public class LiquibaseRecorder {

    public BeanContainerListener setLiquibaseBuildConfig(LiquibaseBuildConfig liquibaseBuildConfig) {
        return beanContainer -> {
            LiquibaseProducer producer = beanContainer.instance(LiquibaseProducer.class);
            producer.setLiquibaseBuildConfig(liquibaseBuildConfig);
        };
    }

    public void doStartActions(LiquibaseRuntimeConfig config, BeanContainer container) {
        if (config.migrateAtStart) {
            Liquibase liquibase = container.instance(Liquibase.class);
            try {
                liquibase.update(config.context);
            }
            catch (LiquibaseException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
