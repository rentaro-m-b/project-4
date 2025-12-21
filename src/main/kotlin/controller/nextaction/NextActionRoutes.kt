package com.example.controller.nextaction

import com.example.controller.common.ErrorResponse
import com.example.controller.nextaction.dto.CreateNextActionRequest
import com.example.controller.nextaction.dto.ListNextActionsResponse
import com.example.controller.nextaction.dto.UpdateNextActionRequest
import com.example.usecase.common.CurrentNextActionNotFoundException
import com.example.usecase.common.usecase.common.CurrentStickyNoteNotFoundException
import com.example.usecase.nextaction.CreateNextActionUseCase
import com.example.usecase.nextaction.DeleteNextActionCommand
import com.example.usecase.nextaction.DeleteNextActionUseCase
import com.example.usecase.nextaction.ListNextActionsUseCase
import com.example.usecase.nextaction.UpdateNextActionUseCase
import io.ktor.http.HttpStatusCode.Companion.Created
import io.ktor.http.HttpStatusCode.Companion.NoContent
import io.ktor.http.HttpStatusCode.Companion.NotFound
import io.ktor.http.HttpStatusCode.Companion.OK
import io.ktor.server.request.receive
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

fun Route.nextActionRoutes() {
    val listNextActionsUseCase by inject<ListNextActionsUseCase>()
    val createNextActionUseCase by inject<CreateNextActionUseCase>()
    val updateNextActionUseCase by inject<UpdateNextActionUseCase>()
    val deleteNextActionUseCase by inject<DeleteNextActionUseCase>()

    route("/next-actions") {
        get {
            val nextActions = listNextActionsUseCase.handle()
            call.response.status(OK)
            call.respond(nextActions.map { ListNextActionsResponse.of(it) })
        }
        route("/{id}") {
            put {
                val id: UUID by call.parameters
                val request = call.receive<UpdateNextActionRequest>()
                val result = updateNextActionUseCase.handle(request.toCommand(id))
                when (result.exceptionOrNull()) {
                    is CurrentNextActionNotFoundException -> {
                        call.response.status(NotFound)
                        call.respond(
                            ErrorResponse(
                                type = "blanck",
                                title = "Not found next action.",
                                detail = "No next action matching the id was found.",
                                instance = "/next-actions/$id",
                            ),
                        )
                    }

                    null -> {}
                }
                call.respond(NoContent)
            }

            delete {
                val id: UUID by call.parameters
                deleteNextActionUseCase.handle(DeleteNextActionCommand(id))
                call.respond(NoContent)
            }
        }
    }
    route("/sticky-notes/{id}/next-actions") {
        post {
            val id: UUID by call.parameters
            val request = call.receive<CreateNextActionRequest>()
            val result = createNextActionUseCase.handle(request.toCommand(id))
            when (result.exceptionOrNull()) {
                is CurrentStickyNoteNotFoundException -> {
                    call.response.status(NotFound)
                    call.respond(
                        ErrorResponse(
                            type = "blanck",
                            title = "Not found sticky note.",
                            detail = "No sticky note matching the id was found.",
                            instance = "/sticky-notes/$id/next-actions",
                        ),
                    )
                }

                null -> {}
            }
            call.respond(Created)
        }
    }
}
