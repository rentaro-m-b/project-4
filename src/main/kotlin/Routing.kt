package com.example

import com.example.controller.stickynote.stickyNoteRoutes
import com.example.infra.stickynote.StickyNoteRepositoryImpl
import com.example.usecase.stickynote.CreateStickyNoteUseCase
import com.example.usecase.stickynote.ListStickyNotesUseCase
import com.example.usecase.stickynote.UpdateStickyNoteUseCase
import io.ktor.http.HttpStatusCode.Companion.OK
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    val config = environment.config
    val url = config.property("db.url")
    val user = config.property("db.user")
    val password = config.property("db.password")
    val dataSource =
        DataSource(
            url = url,
            user = user,
            password = password,
        )

    val stickyNoteRepository = StickyNoteRepositoryImpl(dataSource)
    val listStickyNoteUseCase = ListStickyNotesUseCase(stickyNoteRepository)
    val createStickyNoteUseCase = CreateStickyNoteUseCase(stickyNoteRepository)
    val updateStickyNoteUseCase = UpdateStickyNoteUseCase(stickyNoteRepository)

    routing {
        get("/health") {
            call.response.status(OK)
            call.respond("Hello, World!")
        }
        stickyNoteRoutes(
            listStickyNoteUseCase,
            createStickyNoteUseCase,
            updateStickyNoteUseCase,
        )
    }
}
