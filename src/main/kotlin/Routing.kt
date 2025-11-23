package com.example

import com.example.controller.stickynote.stickyNoteRoutes
import com.example.infra.stickynote.StickyNoteRepositoryImpl
import com.example.usecase.stickynote.StickyNoteUseCase
import io.ktor.http.HttpStatusCode.Companion.OK
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    val stickyNoteUseCase = StickyNoteUseCase(StickyNoteRepositoryImpl())

    routing {
        get("/health") {
            call.response.status(OK)
            call.respond("Hello, World!")
        }
        stickyNoteRoutes(stickyNoteUseCase)
    }
}
