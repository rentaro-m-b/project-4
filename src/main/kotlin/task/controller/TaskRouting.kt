package com.example.task.controller

import io.ktor.http.HttpStatusCode.Companion.Created
import io.ktor.server.request.receive
import io.ktor.server.response.header
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.resources.post

fun Route.taskRoute() {
    post<CreateTaskResource> {
        val request = call.receive<CreateTaskRequest>()
        call.response.header("Location", handleCreateTaskRequest(request).toString())
        call.respond(Created)
    }
}
