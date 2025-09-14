package com.example

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.ktor.server.application.Application
import io.ktor.server.config.ApplicationConfig
import org.jooq.DSLContext
import org.jooq.SQLDialect
import org.jooq.impl.DSL
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.ktor.plugin.koinModule
import javax.sql.DataSource

fun Application.mainModule() {
    val dbConfig: ApplicationConfig = environment.config.config("db")

    fun provideDataSource(): DataSource =
        HikariDataSource(
            HikariConfig().apply {
                jdbcUrl = dbConfig.property("url").getString()
                username = dbConfig.property("user").getString()
                password = dbConfig.property("password").getString()
                driverClassName = dbConfig.property("driver").getString()
                maximumPoolSize =
                    dbConfig
                        .config("pool")
                        .property("maxSize")
                        .getString()
                        .toInt()
            },
        )

    fun provideDslContext(dataSource: DataSource): DSLContext = DSL.using(dataSource, SQLDialect.POSTGRES)

    koinModule {
        singleOf(::provideDataSource) bind DataSource::class
        singleOf(::provideDslContext) bind DSLContext::class
    }
}
