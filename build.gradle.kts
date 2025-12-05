plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.plugin.serialization)
    alias(libs.plugins.ktor)
    alias(libs.plugins.flyway)
    alias(libs.plugins.jooq.codegen)
}

group = "com.example"
version = "0.0.1"

application {
    mainClass = "io.ktor.server.netty.EngineMain"
}

dependencies {
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.kotlinx.datetime)
    implementation(libs.ktor.server.core)
    implementation(libs.ktor.server.netty)
    implementation(libs.ktor.server.config.yaml)
    implementation(libs.ktor.server.content.negotiation)
    implementation(libs.ktor.serialization.kotlinx.json)
    implementation(libs.logback.classic)
    implementation(libs.postgresql)
    implementation(libs.hikari)
    implementation(libs.jooq)
    implementation(libs.jooq.meta)
    jooqCodegen(libs.postgresql)
    testImplementation(libs.groovy)
    testImplementation(libs.junit.jupiter.api)
    testImplementation(libs.kotlin.test)
    testImplementation(libs.ktor.server.test.host)
    testImplementation(libs.ktor.client.content.negotiation)
    testImplementation(libs.rider.core)
    testImplementation(libs.snakeyaml)
    testImplementation(libs.jackson.core)
    testImplementation(libs.rider.junit5)
}

buildscript {
    dependencies {
        classpath(libs.flyway.database.postgresql)
    }
}

tasks.test {
    useJUnitPlatform()
}

flyway {
    url = "jdbc:postgresql://localhost:54332/main"
    user = "montre"
    password = "P@ssw0rd"
}

sourceSets {
    main {
        java.srcDir("./build/generated/jooq")
    }
}

jooq {
    configuration {
        jdbc {
            driver = "org.postgresql.Driver"
            url = "jdbc:postgresql://localhost:54332/main"
            user = "montre"
            password = "P@ssw0rd"
        }

        generator {
            database {
                name = "org.jooq.meta.postgres.PostgresDatabase"
                includes = ".*"
                excludes = "flyway_schema_history"
                inputSchema = "public"
            }

            target {
                packageName = group.toString()
                directory = "./build/generated/jooq"
            }
        }
    }
}
