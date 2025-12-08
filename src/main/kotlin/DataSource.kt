package com.example

import io.ktor.server.application.*

fun Application.configureDataSource(): DataSource {
    val config = environment.config
    val url = config.property("db.url")
    val user = config.property("db.user")
    val password = config.property("db.password")
    return DataSource(
        url = url,
        user = user,
        password = password,
    )
}