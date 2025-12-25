package com.example.controller.stickynote

import com.example.controller.common.ErrorResponse
import com.example.controller.stickynote.dto.CreateStickyNoteRequest
import com.example.controller.stickynote.dto.ListStickyNotesResponse
import com.example.controller.stickynote.dto.UpdateStickyNoteRequest
import com.example.usecase.common.CurrentStickyNoteNotFoundException
import com.example.usecase.stickynote.CreateStickyNoteUseCase
import com.example.usecase.stickynote.DeleteStickyNoteCommand
import com.example.usecase.stickynote.DeleteStickyNoteUseCase
import com.example.usecase.stickynote.ListStickyNotesUseCase
import com.example.usecase.stickynote.UpdateStickyNoteUseCase
import io.ktor.http.HttpHeaders.Location
import io.ktor.http.HttpStatusCode.Companion.Created
import io.ktor.http.HttpStatusCode.Companion.NoContent
import io.ktor.http.HttpStatusCode.Companion.NotFound
import io.ktor.http.HttpStatusCode.Companion.OK
import io.ktor.server.request.receive
import io.ktor.server.response.header
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.delete
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.put
import io.ktor.server.routing.route
import io.ktor.server.util.getValue
import org.koin.ktor.ext.inject
import java.util.UUID

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
            val id = createStickyNoteUseCase.handle(request.toCommand())
            call.response.status(Created)
            call.response.header(Location, "/sticky-notes/$id")
        }

        route("/{id}") {
            put {
                val id: UUID by call.parameters
                val request = call.receive<UpdateStickyNoteRequest>()
                val result = updateStickyNoteUseCase.handle(request.toCommand(id))
                result.fold(
                    onSuccess = {
                        call.respond(NoContent)
                    },
                    onFailure = {
                        when (it) {
                            is CurrentStickyNoteNotFoundException -> {
                                call.response.status(NotFound)
                                call.respond(
                                    ErrorResponse(
                                        type = "blanck",
                                        title = "Not found sticky note.",
                                        detail = "No sticky note matching the id was found.",
                                        instance = "/sticky-notes/$id",
                                    ),
                                )
                            }
                        }
                    },
                )
            }

            delete {
                val id: UUID by call.parameters
                deleteStickyNoteUseCase.handle(DeleteStickyNoteCommand(id))
                call.respond(NoContent)
            }
        }
    }
}
