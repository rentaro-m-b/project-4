package com.example.infra

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.ktor.server.application.Application
import io.ktor.server.config.ApplicationConfig
import java.sql.Connection

class DataSource(applicationConfig: ApplicationConfig) {
    private val config = HikariConfig()
    private val dataSource: HikariDataSource

    init {
        config.jdbcUrl = applicationConfig.property("db.url").getString()
        config.username = applicationConfig.property("db.user").getString()
        config.password = applicationConfig.property("db.password").getString()
        dataSource = HikariDataSource(config)
    }

    fun getConnection(): Connection = dataSource.connection
}
