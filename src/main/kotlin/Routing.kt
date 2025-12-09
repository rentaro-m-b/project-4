package com.example

import com.example.controller.stickynote.stickyNoteRoutes
import io.ktor.http.HttpStatusCode.Companion.OK
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    routing {
        get("/health") {
            call.response.status(OK)
            call.respond("Hello, World!")
        }
        stickyNoteRoutes()
    }
}
