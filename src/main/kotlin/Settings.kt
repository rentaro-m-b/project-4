package com.example

import io.ktor.server.application.Application
import io.ktor.server.config.ApplicationConfig

fun Application.readDbSettings(): DBSettings = DBSettings.of(environment.config.config("db"))

data class DBSettings(
    val url: String,
    val user: String,
    val password: String,
    val driver: String,
    val maxPoolSize: Int,
) {
    companion object {
        fun of(config: ApplicationConfig): DBSettings =
            DBSettings(
                url = config.property("url").getString(),
                user = config.property("user").getString(),
                password = config.property("password").getString(),
                driver = config.property("driver").getString(),
                maxPoolSize =
                    config
                        .config("pool")
                        .property("maxSize")
                        .getString()
                        .toInt(),
            )
    }
}
