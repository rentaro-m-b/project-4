package com.example

import com.example.controller.nextaction.nextActionRoutes
import com.example.controller.scheduledaction.scheduledActionRoutes
import com.example.controller.stickynote.stickyNoteRoutes
import io.ktor.http.HttpStatusCode.Companion.OK
import io.ktor.server.application.Application
import io.ktor.server.response.respond
import io.ktor.server.routing.get
import io.ktor.server.routing.routing

fun Application.configureRouting() {
    routing {
        get("/health") {
            call.response.status(OK)
            call.respond("Hello, World!")
        }
        stickyNoteRoutes()
        nextActionRoutes()
        scheduledActionRoutes()
    }
}
