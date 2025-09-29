package com.example.task.controller

import io.ktor.http.HttpStatusCode.Companion.Created
import io.ktor.resources.Resource
import io.ktor.server.request.receive
import io.ktor.server.resources.post
import io.ktor.server.response.header
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.netty.handler.codec.http.HttpHeaderNames.LOCATION
import org.koin.ktor.ext.inject

@Resource("/tasks")
class CreateTaskResource

fun Route.taskRoute() {
    post<CreateTaskResource> {
        val handler by inject<CreateTaskRequestHandler>()
        val request = call.receive<CreateTaskRequest>()
        call.response.header(LOCATION.toString(), handler.handle(request).toString())
        call.respond(Created)
    }
}
