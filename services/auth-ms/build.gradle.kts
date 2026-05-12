import java.sql.DriverManager

plugins {
    java
    id("org.springframework.boot") version "4.0.6"
    id("io.spring.dependency-management") version "1.1.7"

    id("org.liquibase.gradle") version "3.1.0"
    id("checkstyle")
}

group = "ru.ci_trainee"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

repositories {
    mavenCentral()
    repositories {
        mavenCentral()
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/Digital-Hustle/exception-starter")
            credentials {
                username = providers.gradleProperty("gpr.user")
                    .orElse(System.getenv("GITHUB_USER")).get()
                password = providers.gradleProperty("gpr.key")
                    .orElse(System.getenv("GITHUB_TOKEN")).get()
            }
        }
    }
}

extra["springCloudVersion"] = "2025.1.0"

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

//
val springProfile = getEnv("SPRING_PROFILES_ACTIVE", "")

// db connection
val dbHost = getEnv("DB_HOST", "localhost")
val dbPort = getEnv("DB_PORT", "5432")
val dbUser = getEnv("DB_USERNAME", "postgres")
val dbPassword = getEnv("DB_PASSWORD", "root")
val dbName = getEnv("DB_NAME", "auth_user_db")
val dbSchema = getEnv("DB_SCHEMA", "auth_user")
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

dependencies {

    // boot starters
    implementation(libs.spring.boot.starter.actuator)
    implementation(libs.spring.boot.starter.data.jpa)
    implementation(libs.spring.boot.starter.security)
    implementation(libs.spring.boot.starter.web)
    implementation(libs.spring.boot.starter.validation)
    implementation(libs.spring.boot.starter.mail)

    // cloud starters
    implementation(libs.spring.cloud.starter.config)

    // custom
    implementation("ru.digital-hustle:exceptions-starter:0.0.1-SNAPSHOT")

    // swagger
    implementation(libs.springdoc)

    // jackson
    implementation(libs.jackson.databind)

    // shedlock
    implementation(libs.shedlock)
    implementation(libs.shedlock.provider)

    // micrometer
    implementation(libs.micrometer.registry.prometheus)
    implementation(libs.micrometer.core)

    // db
    runtimeOnly(libs.postgresql)

    // liquibase
    liquibaseRuntime(libs.bundles.liquibase.runtime)

    // mapper
    implementation(libs.mapstruct)
    annotationProcessor(libs.mapstruct.processor)

    // lombok
    compileOnly(libs.lombok)
    annotationProcessor(libs.lombok)
    annotationProcessor(libs.lombok.mapstruct.binding)

    // JWT
    implementation(libs.bundles.jwt)   // bundle для JWT

    // test
    testImplementation(libs.mockito.core)
    testImplementation(libs.spring.boot.starter.test)
    testImplementation(libs.spring.security.test)
    testRuntimeOnly(libs.junit.platform.launcher)
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

checkstyle {
    val checkstyleDir = rootProject.projectDir.resolve("checkstyle")
    configFile = checkstyleDir.resolve("checkstyle.xml")
    configDirectory = checkstyleDir

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


dependencyManagement {
    imports {
        mavenBom("org.springframework.cloud:spring-cloud-dependencies:${property("springCloudVersion")}")
    }
}

tasks.test {
    jvmArgs = listOf(
        "--add-opens=java.base/java.lang=ALL-UNNAMED",
        "--add-opens=java.base/java.util=ALL-UNNAMED",
        "-Djdk.attach.allowAttachSelf=true"
    )
    useJUnitPlatform()
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
    options.compilerArgs.add("-parameters")
}

tasks.named<Checkstyle>("checkstyleMain") {
    source = sourceSets.main.get().allJava
    classpath = configurations.compileClasspath.get()
}

tasks.named<Checkstyle>("checkstyleTest") {
    enabled = false
}
