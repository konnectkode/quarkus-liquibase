# Quarkus-Liquibase - A extension Liquibase extension for quarkus framework

[![CircleCI](https://img.shields.io/circleci/build/github/konnectkode/quarkus-liquibase?style=for-the-badge)](https://circleci.com/gh/konnectkode/quarkus-liquibase)
[![Codecov](https://img.shields.io/codecov/c/github/konnectkode/quarkus-liquibase?style=for-the-badge)](https://codecov.io/gh/konnectkode/quarkus-liquibase)
[![Maven Central](https://img.shields.io/maven-central/v/com.konnectkode/quarkus-liquibase?style=for-the-badge)](https://search.maven.org/artifact/com.konnectkode/quarkus-liquibase/1.0.0.CR2/jar)
[![GitHub](https://img.shields.io/github/license/konnectkode/quarkus-liquibase?style=for-the-badge)]((https://www.apache.org/licenses/LICENSE-2.0))

### Using Quarkus Liquibase

OBS: THIS LIBRARY DOES NOT SUPPORT NATIVE IMAGE

#### Setting up support for Liquibase

To start using Liquibase with your project, you just need to: 

* add your migrations to the src/main/resources/db/migration
* activate the migrate-at-start option to migrate the schema automatically or inject the Liquibase object and run your migration

In your pom.xml, add the following dependencies:

* the Liquibase extension
* your JDBC driver extension

```xml
<dependencies>
    <!-- Liquibase specific dependencies -->
    <dependency>
        <groupId>com.konnectkode</groupId>
        <artifactId>quarkus-liquibase</artifactId>
    </dependency>

    <!-- JDBC driver dependencies -->
    <dependency>
        <groupId>io.quarkus</groupId>
        <artifactId>quarkus-jdbc-postgresql</artifactId>
    </dependency>
</dependencies>
```

Liquibase support relies on the Quarkus default datasource config, you must add the default datasource properties to the application.properties file in order to allow Liquibase to manage the schema. Also, you can customize the Liquibase behaviour by using the following properties:

**quarkus.liquibase.migrate-at-start**  
> **true**  to execute Liquibase automatically when the application starts, false otherwise.
> **default:** false

**quarkus.liquibase.changelog**
> Changelog file path that contains the liquibase migrations  
> **default:** classpath:db/migration

**quarkus.liquibase.default-schema**
> The name of the default schema. By default the liquibase tables are placed in the default schema for the connection provided by the datasource.
> When the quarkus.liquibase.default-schema property is set, the tables are placed in the provided schema.  
> **default:** \<none>

**quarkus.liquibase.contexts**
> Liquibase supports contexts a comma-separated list of contexts that works like profiles ([Liquibase Contexts](https://www.liquibase.org/documentation/contexts.html)).  
> **Default:** \<none>

The following is an example for the *application.properties* file:

```properties
quarkus.datasource.url=jdbc:postgresql://localhost:5432/database
quarkus.datasource.driver=org.postgresql.Driver
quarkus.datasource.username=liquibase
quarkus.datasource.password=liquibase

# Liquibase minimal config properties
#quarkus.liquibase.changelog=db/changelog.xml
#quarkus.liquibase.default-schema=production
#quarkus.liquibase.contexts=production,migration
```