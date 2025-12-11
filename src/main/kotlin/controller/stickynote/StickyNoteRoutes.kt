package com.example.controller.stickynote

import com.example.usecase.stickynote.CreateStickyNoteUseCase
import com.example.usecase.stickynote.DeleteStickyNoteCommand
import com.example.usecase.stickynote.DeleteStickyNoteUseCase
import com.example.usecase.stickynote.ListStickyNotesUseCase
import com.example.usecase.stickynote.UpdateStickyNoteUseCase
import io.ktor.http.HttpStatusCode.Companion.Created
import io.ktor.http.HttpStatusCode.Companion.NoContent
import io.ktor.http.HttpStatusCode.Companion.OK
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.util.*
import org.koin.ktor.ext.inject
import java.util.*
import kotlin.getValue

fun Route.stickyNoteRoutes() {
    val listStickyNotesUseCase by inject<ListStickyNotesUseCase>()
    val createStickyNoteUseCase by inject<CreateStickyNoteUseCase>()
    val updateStickyNoteUseCase by inject<UpdateStickyNoteUseCase>()
    val deleteStickyNoteUseCase by inject<DeleteStickyNoteUseCase>()

    route("/sticky-notes") {
        get {
            val stickyNotes = listStickyNotesUseCase.handle()
            call.response.status(OK)
            call.respond(stickyNotes.map { ListStickyNotesResponse.of(it) })
        }

        post {
            val request = call.receive<CreateStickyNoteRequest>()
            createStickyNoteUseCase.handle(request.toCommand())
            call.respond(Created)
        }

        route("/{id}") {
            put {
                val id: UUID by call.parameters
                val request = call.receive<UpdateStickyNoteRequest>()
                updateStickyNoteUseCase.handle(request.toCommand(id))
                call.respond(NoContent)
            }

            delete {
                val id: UUID by call.parameters
                deleteStickyNoteUseCase.handle(DeleteStickyNoteCommand(id))
                call.respond(NoContent)
            }
        }
    }
}
