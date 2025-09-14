package com.example

import com.example.common.commonModule
import com.example.task.controller.taskRoute
import com.example.task.taskModule
import io.ktor.http.HttpStatusCode.Companion.OK
import io.ktor.resources.Resource
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.server.resources.Resources
import io.ktor.server.resources.get
import io.ktor.server.response.respond
import io.ktor.server.routing.routing
import org.koin.ktor.plugin.Koin

fun Application.configureRouting() {
    install(ContentNegotiation) { json() }
    install(Resources)
    install(Koin)

    routing {
        get<HealthCheckResource> {
            call.respond(OK)
        }

        taskRoute()
    }

    mainModule()
    taskModule()
    commonModule()
}

@Resource("/health")
class HealthCheckResource
