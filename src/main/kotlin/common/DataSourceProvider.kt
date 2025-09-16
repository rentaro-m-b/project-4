package com.example.common

import com.example.DBSettings
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.ktor.server.config.ApplicationConfig
import javax.sql.DataSource

object DataSourceProvider {
    fun provideDataSource(settings: DBSettings): DataSource =
        HikariDataSource(
            HikariConfig().apply {
                jdbcUrl = settings.url
                username = settings.user
                password = settings.password
                driverClassName = settings.driver
                maximumPoolSize = settings.maxPoolSize
            },
        )
}
