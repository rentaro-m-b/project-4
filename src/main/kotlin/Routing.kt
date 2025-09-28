package com.example

import com.example.common.CommonDIContainer
import com.example.task.TaskDIContainer
import com.example.task.controller.taskRoute
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

@Resource("/health")
class HealthCheckResource

fun Application.configureRouting() {
    install(ContentNegotiation) { json() }
    install(Resources)
    install(Koin) {
        modules(
            listOf(
                TaskDIContainer.defineModule(),
                CommonDIContainer.defineModule(readDbSettings()),
            ),
        )
    }
    println("install koin OK")

    routing {
        get<HealthCheckResource> {
            call.respond(OK)
        }

        taskRoute()
    }
}
