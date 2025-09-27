plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.ktor)
    alias(libs.plugins.kotest)
    alias(libs.plugins.kotlin.plugin.serialization)
    alias(libs.plugins.flyway)
    alias(libs.plugins.jooq.codegen)
}

group = "com.example"
version = "0.0.1"

application {
    mainClass = "io.ktor.server.netty.EngineMain"
}

buildscript {
    repositories { mavenCentral() }
    dependencies {
        classpath(libs.postgresql)
        classpath(libs.flyway.database.postgresql)
    }
}

dependencies {
    implementation(libs.ktor.server.core)
    implementation(libs.ktor.server.netty)
    implementation(libs.logback.classic)
    implementation(libs.ktor.server.config.yaml)
    implementation(libs.ktor.server.status.pages)
    implementation(libs.ktor.server.content.negotiation)
    implementation(libs.ktor.server.resources)
    implementation(libs.ktor.serialization.kotlinx.json)
    implementation(libs.ktor.server.host.common)
    implementation(libs.koin.ktor)
    implementation(libs.koin.logger.slf4j)
    implementation(libs.postgresql)
    implementation(libs.jooq)
    implementation(libs.hikaricp)
    testImplementation(libs.postgresql)
    testImplementation(libs.jooq)
    testImplementation(libs.ktor.server.config.yaml)
    testImplementation(libs.ktor.server.test.host)
    testImplementation(libs.kotlin.test.junit)
    testImplementation(libs.kotest.framework.engine)
    testImplementation(libs.kotest.assertions.core)
    testImplementation(libs.rest.assured)
    testImplementation(libs.koin.test)
    testImplementation(libs.kotest.extensions.koin)
    testImplementation(libs.mockk)
    testImplementation(libs.testcontainers.postgresql)
    testImplementation(libs.commons.compress)
    testImplementation(libs.flyway.database.postgresql)
    testImplementation(libs.ktor.client.core)
    testImplementation(libs.ktor.client.content.negotiation)
    testImplementation(libs.ktor.serialization.kotlinx.json)
    jooqCodegen(libs.postgresql)
    jooqCodegen(libs.jooq.meta)
    jooqCodegen(libs.jooq.codegen)
}

flyway {
    url = "jdbc:postgresql://localhost:54332/main"
    user = "root"
    password = "password"
    locations = arrayOf("filesystem:src/main/resources/db/migration")

    cleanDisabled = false
}

val codeGenDir = layout.buildDirectory.dir("generated/jooq")

jooq {
    configuration {
        jdbc {
            driver = "org.postgresql.Driver"
            url = "jdbc:postgresql://localhost:54332/main"
            user = "root"
            password = "password"
        }
        generator {
            name = "org.jooq.codegen.KotlinGenerator"
            database {
                name = "org.jooq.meta.postgres.PostgresDatabase"
                inputSchema = "public"
                includes = ".*"
                excludes = "flyway_schema_history"
            }
            target {
                packageName = "com.example.db"
                directory = codeGenDir.get().asFile.path
            }
        }
    }
}

sourceSets.main {
    kotlin.srcDirs(codeGenDir)
}

tasks.named("compileKotlin") {
    dependsOn("jooqCodegen")
}

tasks.test {
    useJUnitPlatform()
    testLogging { showStandardStreams = true }
}
