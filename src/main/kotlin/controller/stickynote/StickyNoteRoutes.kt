package com.example.controller.stickynote

import com.example.usecase.stickynote.StickyNoteUseCase
import io.ktor.http.HttpStatusCode.Companion.OK
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.stickyNoteRoutes(
    stickyNoteUseCase: StickyNoteUseCase,
) {
    route("/stickyNotes") {
        listStickyNotes(stickyNoteUseCase)
    }
}

fun Route.listStickyNotes(useCase: StickyNoteUseCase) {
    get {
        val stickyNotes = useCase.handle()
        call.response.status(OK)
        call.respond(stickyNotes)
    }
}
