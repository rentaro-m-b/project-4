package com.example.controller.stickynote

import com.example.domain.stickynote.StickyNote
import com.example.usecase.stickynote.CreateStickyNoteUseCase
import com.example.usecase.stickynote.ListStickyNotesUseCase
import io.ktor.http.HttpStatusCode.Companion.Created
import io.ktor.http.HttpStatusCode.Companion.OK
import io.ktor.server.request.receive
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock.System.now
import kotlinx.serialization.Serializable
import kotlin.time.ExperimentalTime

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
        call.respond(stickyNotes)
    }
}

@OptIn(ExperimentalTime::class)
fun Route.createStickyNote(useCase: CreateStickyNoteUseCase) {
    post {
        val request = call.receive<CreateStickyNoteRequest>()
        useCase.handle(StickyNote(request.concern, now().toLocalDateTime(timeZone = TimeZone.UTC)))
        call.respond(Created)
    }
}

@Serializable
data class CreateStickyNoteRequest(
    val concern: String,
)
