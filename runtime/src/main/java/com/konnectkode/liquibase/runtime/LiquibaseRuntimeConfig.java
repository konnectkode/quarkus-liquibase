package com.konnectkode.liquibase.runtime;

import io.quarkus.runtime.annotations.ConfigItem;
import io.quarkus.runtime.annotations.ConfigPhase;
import io.quarkus.runtime.annotations.ConfigRoot;

@ConfigRoot(name = "liquibase", phase = ConfigPhase.RUN_TIME)
public final class LiquibaseRuntimeConfig {

    /**
     * true to execute Flyway automatically when the application starts, false otherwise.
     *
     */
    @ConfigItem
    public boolean migrateAtStart;

    /**
     * Liquibase migration profile.
     */
    @ConfigItem(defaultValue = "production")
    public String contexts;

}
