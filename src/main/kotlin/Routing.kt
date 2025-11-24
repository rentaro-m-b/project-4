package com.example

import com.example.controller.stickynote.stickyNoteRoutes
import com.example.domain.stickynote.StickyNoteRepository
import com.example.infra.stickynote.StickyNoteRepositoryImpl
import com.example.usecase.stickynote.CreateStickyNoteUseCase
import com.example.usecase.stickynote.ListStickyNotesUseCase
import io.ktor.http.HttpStatusCode.Companion.OK
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    val listStickyNoteUseCase = ListStickyNotesUseCase(StickyNoteRepositoryImpl)
    val createStickyNoteUseCase = CreateStickyNoteUseCase(StickyNoteRepositoryImpl)

    routing {
        get("/health") {
            call.response.status(OK)
            call.respond("Hello, World!")
        }
        stickyNoteRoutes(
            listStickyNoteUseCase,
            createStickyNoteUseCase,
        )
    }
}
