package com.konnectkode.liquibase;

import com.konnectkode.liquibase.runtime.LiquibaseBuildConfig;
import com.konnectkode.liquibase.runtime.LiquibaseProducer;
import com.konnectkode.liquibase.runtime.LiquibaseRecorder;
import com.konnectkode.liquibase.runtime.LiquibaseRuntimeConfig;
import io.quarkus.agroal.deployment.DataSourceInitializedBuildItem;
import io.quarkus.arc.deployment.AdditionalBeanBuildItem;
import io.quarkus.arc.deployment.BeanContainerBuildItem;
import io.quarkus.arc.deployment.BeanContainerListenerBuildItem;
import io.quarkus.deployment.annotations.BuildProducer;
import io.quarkus.deployment.annotations.BuildStep;
import io.quarkus.deployment.annotations.Record;
import io.quarkus.deployment.builditem.FeatureBuildItem;
import org.jboss.logging.Logger;

import static io.quarkus.deployment.annotations.ExecutionTime.RUNTIME_INIT;
import static io.quarkus.deployment.annotations.ExecutionTime.STATIC_INIT;

class LiquibaseProcessor {

    public static final String LIQUIBASE = "liquibase";

    private static final Logger LOGGER = Logger.getLogger(LiquibaseProcessor.class);

    /**
     * Liquibase build config
     */
    LiquibaseBuildConfig liquibaseBuildConfig;

    @BuildStep
    AdditionalBeanBuildItem register() {
        return AdditionalBeanBuildItem.unremovableOf(LiquibaseProducer.class);
    }

    @Record(STATIC_INIT)
    @BuildStep(providesCapabilities = "com.konnectkode.liquibase")
    void build(BuildProducer<FeatureBuildItem> featureProducer,
               BuildProducer<BeanContainerListenerBuildItem> containerListenerProducer,
               LiquibaseRecorder recorder,
               DataSourceInitializedBuildItem dataSourceInitializedBuildItem) {

        featureProducer.produce(new FeatureBuildItem(LIQUIBASE));

        containerListenerProducer.produce(new BeanContainerListenerBuildItem(recorder.setLiquibaseBuildConfig(liquibaseBuildConfig)));
    }

    @Record(RUNTIME_INIT)
    @BuildStep
    void configureRuntimeProperties(LiquibaseRecorder recorder,
                                    LiquibaseRuntimeConfig liquibaseRuntimeConfig,
                                    BeanContainerBuildItem beanContainer) {
        recorder.doStartActions(liquibaseRuntimeConfig, beanContainer.getValue());
    }

}
