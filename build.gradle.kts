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
    implementation(libs.jooq)
    implementation(libs.jooq.meta)
    implementation(libs.jooq.codegen)
    runtimeOnly(libs.postgresql)
    jooqCodegen(libs.postgresql)
    testImplementation(libs.kotlin.test)
    testImplementation(libs.ktor.server.test.host)
    testImplementation(libs.ktor.client.content.negotiation)
}

buildscript {
    dependencies {
        classpath(libs.flyway.database.postgresql)
    }
}

flyway {
    url = "jdbc:postgresql://localhost:54332/main"
    user = "montre"
    password = "P@ssw0rd"
}

jooq {
    configurations {
        jdbc {
            driver = "org.postgresql.Driver"
            url = "jdbc:postgresql://localhost:54332/main"
            user = "montre"
            password = "P@ssw0rd"
        }
    }
}
