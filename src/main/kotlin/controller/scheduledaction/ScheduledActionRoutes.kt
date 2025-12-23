package com.example.controller.scheduledaction

import com.example.controller.common.ErrorResponse
import com.example.controller.scheduledaction.dto.CreateScheduledActionRequest
import com.example.controller.scheduledaction.dto.ListScheduledActionsResponse
import com.example.controller.scheduledaction.dto.UpdateScheduledActionRequest
import com.example.usecase.common.CurrentStickyNoteNotFoundException
import com.example.usecase.common.usecase.common.CurrentScheduledActionNotFoundException
import com.example.usecase.scheduledaction.CreateScheduledActionUseCase
import com.example.usecase.scheduledaction.DeleteScheduledActionCommand
import com.example.usecase.scheduledaction.DeleteScheduledActionUseCase
import com.example.usecase.scheduledaction.ListScheduledActionsUseCase
import com.example.usecase.scheduledaction.UpdateScheduledActionUseCase
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

fun Route.scheduledActionRoutes() {
    val listScheduledActionsUseCase by inject<ListScheduledActionsUseCase>()
    val createScheduledActionUseCase by inject<CreateScheduledActionUseCase>()
    val updateScheduledActionUseCase by inject<UpdateScheduledActionUseCase>()
    val deleteScheduledActionUseCase by inject<DeleteScheduledActionUseCase>()

    route("/scheduled-actions") {
        get {
            val scheduledActions = listScheduledActionsUseCase.handle()
            call.response.status(OK)
            call.respond(scheduledActions.map { ListScheduledActionsResponse.of(it) })
        }
        route("/{id}") {
            put {
                val id: UUID by call.parameters
                val request = call.receive<UpdateScheduledActionRequest>()
                val result = updateScheduledActionUseCase.handle(request.toCommand(id))
                result.fold(
                    onSuccess = {
                        call.respond(NoContent)
                    },
                    onFailure = {
                        when (it) {
                            is CurrentScheduledActionNotFoundException -> {
                                call.response.status(NotFound)
                                call.respond(
                                    ErrorResponse(
                                        type = "blanck",
                                        title = "Not found scheduled action.",
                                        detail = "No scheduled action matching the id was found.",
                                        instance = "/scheduled-actions/$id",
                                    ),
                                )
                            }
                        }
                    },
                )
            }

            delete {
                val id: UUID by call.parameters
                deleteScheduledActionUseCase.handle(DeleteScheduledActionCommand(id))
                call.respond(NoContent)
            }
        }
    }
    route("/sticky-notes/{id}/scheduled-actions") {
        post {
            val id: UUID by call.parameters
            val request = call.receive<CreateScheduledActionRequest>()
            val result = createScheduledActionUseCase.handle(request.toCommand(id))
            result.fold(
                onSuccess = {
                    call.response.status(Created)
                    call.response.header(Location, "/scheduled-actions/$it")
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
                                    instance = "/sticky-notes/$id/scheduled-actions",
                                ),
                            )
                        }
                    }
                },
            )
        }
    }
}
