import org.jooq.meta.jaxb.ForcedType
import org.jooq.meta.jaxb.Generator
import org.jooq.meta.jaxb.Jdbc
import org.jooq.meta.jaxb.Logging
import java.sql.DriverManager

plugins {
    java
    id("org.springframework.boot") version "3.5.7"
    id("io.spring.dependency-management") version "1.1.7"

    id("org.liquibase.gradle") version "3.1.0"
    id("nu.studer.jooq") version "9.0"
    id("checkstyle")
}

group = "rnd.sueta"
version = "0.0.1-SNAPSHOT"
description = "Demo project for Spring Boot"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
}

extra["springCloudVersion"] = "2025.0.0"
extra["jooq.version"] = "3.19.13"

fun loadEnv(envFile: File): Map<String, String> {
    val env = mutableMapOf<String, String>()
    if (envFile.exists()) {
        envFile.forEachLine { line ->
            if (line.isNotBlank() && line.contains("=") && !line.trim().startsWith("#")) {
                val parts = line.split("=", limit = 2)
                if (parts.size == 2) {
                    env[parts[0].trim()] = parts[1].trim()
                }
            }
        }
    }
    return env
}

fun getEnv(key: String, defaultValue: String = ""): String {
    return envVars[key] ?: System.getenv(key) ?: defaultValue
}

val envFile = file(".env")
val envVars = loadEnv(envFile)

// versions
val postgresVersion = "42.6.0"
val minioVersion = "8.5.7"
val jakartaPersistenceVersion = "3.2.0"
val jooqVersion = "3.19.13"
val jacksonDatabindVersion = "2.15.2"
val liquibaseVersion = "4.33.0"
val mapstructVersion = "1.6.3"
val apacheCommonsIoVersion = "2.21.0"
val apacheCommonsValidatorVersion = "1.10.0"
val libphonenumberVersion = "9.0.24"
val springdocVersion = "2.8.13"
val lombokMapstructBindingVersion = "0.2.0"

//
val springProfile = getEnv("SPRING_PROFILES_ACTIVE", "")

// db connection
val dbHost = getEnv("DB_HOST", "localhost")
val dbPort = getEnv("DB_PORT", "5432")
val dbUser = getEnv("DB_USERNAME", "postgres")
val dbPassword = getEnv("DB_PASSWORD", "root")
val dbName = getEnv("DB_NAME", "event_db")
val dbSchema = getEnv("DB_SCHEMA", "event")
val dbDriver = getEnv("DB_DRIVER", "org.postgresql.Driver")

val dbUrl = "jdbc:postgresql://$dbHost:$dbPort/$dbName"

// paths
val checkstylePath = "../../checkstyle"
val changelogMasterPath = "src/main/resources/db/changelog"

// fileNames
val changelogFileName = when (springProfile) {
    "dev" -> "db.changelog-master.dev.yml"
    else -> "db.changelog-master.yml" // неизвестный профиль
}

// ci switcher
val isCI = System.getenv("CI") == "true"
val isGitLabCI = System.getenv("GITLAB_CI") == "true"

dependencies {
    // boot starters
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-aop")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-jooq")
    implementation("org.springframework.boot:spring-boot-starter-webflux")

    // cloud starters
    implementation("org.springframework.cloud:spring-cloud-starter-config")

    // spring
    implementation("org.springframework.data:spring-data-commons")

    // persist
    implementation("jakarta.persistence:jakarta.persistence-api:$jakartaPersistenceVersion")

    // minio
    implementation("io.minio:minio:$minioVersion")

    // db
    runtimeOnly("org.postgresql:postgresql:$postgresVersion")
    jooqGenerator("org.postgresql:postgresql:$postgresVersion")
    implementation("org.jooq:jooq-jackson-extensions:$jooqVersion")
    implementation("com.fasterxml.jackson.core:jackson-databind:$jacksonDatabindVersion")

    // liquibase
    liquibaseRuntime("org.postgresql:postgresql:$postgresVersion")
    liquibaseRuntime("org.liquibase:liquibase-core:$liquibaseVersion")
    liquibaseRuntime("org.liquibase:liquibase-commercial:$liquibaseVersion")
    liquibaseRuntime("info.picocli:picocli:4.7.5")

    // mapper
    implementation("org.mapstruct:mapstruct:$mapstructVersion")
    annotationProcessor("org.mapstruct:mapstruct-processor:$mapstructVersion")

    // utils
    implementation("commons-io:commons-io:$apacheCommonsIoVersion")
    implementation("commons-validator:commons-validator:$apacheCommonsValidatorVersion")
    implementation("com.googlecode.libphonenumber:libphonenumber:$libphonenumberVersion")

    // swagger
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:$springdocVersion")

    // lombok
    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok-mapstruct-binding:$lombokMapstructBindingVersion")

    // tests
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

dependencyManagement {
    imports {
        mavenBom("org.springframework.cloud:spring-cloud-dependencies:${property("springCloudVersion")}")
    }
}

buildscript {
    dependencies {
        classpath("org.postgresql:postgresql:42.6.0") // для создания схемы перед liquibase
        classpath("org.liquibase:liquibase-core:4.33.0") // для liquibase плагина
    }
}

// plugins config
liquibase {
    activities {
        create("main") {
            this.arguments = mapOf(
                "changelogFile" to "$changelogMasterPath/$changelogFileName",
                "url" to dbUrl,
                "username" to dbUser,
                "password" to dbPassword,
                "driver" to dbDriver,
                "defaultSchemaName" to dbSchema,
                "liquibaseSchemaName" to dbSchema
            )
        }
    }
    runList = "main"
}

jooq {
    version.set(jooqVersion)

    configurations {
        create("main") {
            jooqConfiguration.apply {
                logging = Logging.WARN
                jdbc = Jdbc().apply {
                    driver = dbDriver
                    url = dbUrl
                    user = dbUser
                    password = dbPassword
                }
                generator = Generator().apply {
                    name = "org.jooq.codegen.DefaultGenerator"
                    database = org.jooq.meta.jaxb.Database().apply {
                        name = "org.jooq.meta.postgres.PostgresDatabase"
                        inputSchema = dbSchema
                        excludes = "databasechangelog|databasechangeloglock"

                        forcedTypes = listOf(
                            ForcedType().apply {
                                userType = "rnd.sueta.event_ms.model.entity.Contacts"
                                isJsonConverter = true
                                includeExpression = ".*\\.contacts"
                                includeTypes = "(?i:jsonb)"
                            }
                        )
                    }
                    generate = org.jooq.meta.jaxb.Generate().apply {
                        isDeprecated = false
                        isRecords = true
                        isImmutablePojos = false
                        isFluentSetters = false
                    }
                    target = org.jooq.meta.jaxb.Target().apply {
                        packageName = "org.jooq.generated"
                        directory = "build/generated-sources"
                    }
                    strategy = org.jooq.meta.jaxb.Strategy().apply {
                        name = "org.jooq.codegen.DefaultGeneratorStrategy"
                    }
                }
            }
        }
    }
}

checkstyle {
    toolVersion = "13.0.0"
    configFile = file("$checkstylePath/checkstyle.xml")
    configDirectory = file(checkstylePath)

    configProperties = mapOf(
        "checkstyle.dir" to file(checkstylePath).absolutePath,
        "checkstyle.cache.file" to layout.buildDirectory.file("checkstyle/cache.properties").get().asFile.absolutePath
    )

    isIgnoreFailures = false
}

// tasks config
tasks.register("ensureSchemaExists") {
    group = "database"

    doLast {
        val jdbcUrl = dbUrl

        DriverManager.getConnection(
            jdbcUrl,
            dbUser,
            dbPassword
        ).use { conn ->
            conn.createStatement().use { stmt ->
                stmt.execute("CREATE SCHEMA IF NOT EXISTS $dbSchema")
            }
        }
    }
}

tasks.register("createCheckstyleCache") {
    val cacheFileProvider = layout.buildDirectory.file("checkstyle/cache.properties")

    doLast {
        val cacheFile = cacheFileProvider.get().asFile
        cacheFile.parentFile.mkdirs()
        if (!cacheFile.exists()) {
            cacheFile.writeText("# Checkstyle cache\n")
        }
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.withType<Checkstyle>().configureEach {
    dependsOn("createCheckstyleCache")
}

tasks.named<Checkstyle>("checkstyleMain") {
    source = sourceSets.main.get().allJava
    classpath = configurations.compileClasspath.get()
}

tasks.named<Checkstyle>("checkstyleTest") {
    enabled = false
}

tasks.named("compileJava") {
    dependsOn("update", "generateJooq")
}

tasks.named("generateJooq") {
    dependsOn("update")
}

tasks.named("check") {
    dependsOn("checkstyleMain")
}

tasks.named("build") {
    dependsOn("check", "compileJava")
}

tasks.named("update") {
    dependsOn("ensureSchemaExists")
}

tasks.named("generateJooq") {
    mustRunAfter("update")
}

tasks.named("checkstyleMain") {
    mustRunAfter("generateJooq")
}

tasks.named("compileJava") {
    mustRunAfter("checkstyleMain")
}
