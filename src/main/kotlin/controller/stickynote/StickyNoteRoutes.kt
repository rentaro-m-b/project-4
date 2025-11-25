package com.example.controller.stickynote

import com.example.usecase.stickynote.CreateStickyNoteUseCase
import com.example.usecase.stickynote.ListStickyNotesUseCase
import io.ktor.http.HttpStatusCode.Companion.Created
import io.ktor.http.HttpStatusCode.Companion.OK
import io.ktor.server.request.receive
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.stickyNoteRoutes(
    listStickyNotesUseCase: ListStickyNotesUseCase,
    createStickyNoteUseCase: CreateStickyNoteUseCase,
) {
    route("/stickyNotes") {
        listStickyNotes(listStickyNotesUseCase)
        createStickyNote(createStickyNoteUseCase)
    }
}

fun Route.listStickyNotes(useCase: ListStickyNotesUseCase) {
    get {
        val stickyNotes = useCase.handle()
        call.response.status(OK)
        call.respond(stickyNotes.map { ListStickyNotesResponse.of(it) })
    }
}

fun Route.createStickyNote(useCase: CreateStickyNoteUseCase) {
    post {
        val request = call.receive<CreateStickyNoteRequest>()
        useCase.handle(request.toCommand())
        call.respond(Created)
    }
}
