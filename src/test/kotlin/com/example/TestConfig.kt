package com.example

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.ktor.server.config.yaml.YamlConfigLoader
import org.jooq.DSLContext
import org.jooq.SQLDialect
import org.jooq.impl.DSL
import javax.sql.DataSource

object TestConfig {
    private val config = YamlConfigLoader().load("application.yaml") ?: throw Exception("not found application.yaml")
    private val dbConfig = config.config("db")

    fun provideDslContext(dataSource: DataSource): DSLContext = DSL.using(dataSource, SQLDialect.POSTGRES)

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
}
