package com.example.controller.stickynote

import com.example.controller.stickynote.UpdateStickyNoteRequest
import com.example.usecase.stickynote.CreateStickyNoteUseCase
import com.example.usecase.stickynote.DeleteStickyNoteUseCase
import com.example.usecase.stickynote.ListStickyNotesUseCase
import com.example.usecase.stickynote.UpdateStickyNoteUseCase
import io.ktor.http.HttpStatusCode.Companion.Created
import io.ktor.http.HttpStatusCode.Companion.OK
import io.ktor.http.HttpStatusCode.Companion.NoContent
import io.ktor.http.content.OutgoingContent
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.util.*
import java.util.*

fun Route.stickyNoteRoutes(
    listStickyNotesUseCase: ListStickyNotesUseCase,
    createStickyNoteUseCase: CreateStickyNoteUseCase,
    updateStickyNoteUseCase: UpdateStickyNoteUseCase,
    deleteStickyNoteUseCase: DeleteStickyNoteUseCase,
) {
    route("/stickyNotes") {
        listStickyNotes(listStickyNotesUseCase)
        createStickyNote(createStickyNoteUseCase)
        updateStickyNote(updateStickyNoteUseCase)
        deleteStickyNote(deleteStickyNoteUseCase)
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

fun Route.updateStickyNote(useCase: UpdateStickyNoteUseCase) {
    put("/{id}") {
        val id: UUID by call.parameters
        val request = call.receive<UpdateStickyNoteRequest>()
        useCase.handle(request.toCommand(id))
        call.respond(NoContent)
    }
}

fun Route.deleteStickyNote(useCase: DeleteStickyNoteUseCase) {
    delete("/{id}") {
        val id: UUID by call.parameters
        val request = call.receive<DeleteStickyNoteRequest>()
        useCase.handle(request.toCommand(id))
        call.respond(NoContent)
    }
}
