package com.konnectkode.liquibase.runtime;

import io.quarkus.runtime.annotations.ConfigItem;
import io.quarkus.runtime.annotations.ConfigPhase;
import io.quarkus.runtime.annotations.ConfigRoot;

import java.util.Optional;

@ConfigRoot(name = "liquibase", phase = ConfigPhase.BUILD_AND_RUN_TIME_FIXED)
public final class LiquibaseBuildConfig {

    /**
     * Liquibase root changelog.
     */
    @ConfigItem
    public String changelog;

    /**
     * Liquibase default schema name.
     */
    @ConfigItem
    public Optional<String> defaultSchema;

}
