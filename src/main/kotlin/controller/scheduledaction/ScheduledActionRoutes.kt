package com.example.controller.scheduledaction

import com.example.usecase.scheduledaction.CreateScheduledActionUseCase
import com.example.usecase.scheduledaction.DeleteScheduledActionCommand
import com.example.usecase.scheduledaction.DeleteScheduledActionUseCase
import com.example.usecase.scheduledaction.ListScheduledActionsUseCase
import com.example.usecase.scheduledaction.UpdateScheduledActionUseCase
import io.ktor.http.HttpStatusCode.Companion.Created
import io.ktor.http.HttpStatusCode.Companion.NoContent
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
import java.util.*

fun Route.scheduledActionRoutes() {
    val listScheduledActionsUseCase by inject<ListScheduledActionsUseCase>()
    val createScheduledActionUseCase by inject<CreateScheduledActionUseCase>()
    val updateScheduledActionUseCase by inject<UpdateScheduledActionUseCase>()
    val deleteScheduledActionUseCase by inject<DeleteScheduledActionUseCase>()

    route("/next-actions") {
        get {
            val ScheduledActions = listScheduledActionsUseCase.handle()
            call.response.status(OK)
            call.respond(ScheduledActions.map { ListScheduledActionsResponse.of(it) })
        }
        route("/{id}") {
            put {
                val id: UUID by call.parameters
                val request = call.receive<UpdateScheduledActionRequest>()
                updateScheduledActionUseCase.handle(request.toCommand(id))
                call.respond(NoContent)
            }

            delete {
                val id: UUID by call.parameters
                deleteScheduledActionUseCase.handle(DeleteScheduledActionCommand(id))
                call.respond(NoContent)
            }
        }
    }
    route("/sticky-notes/{id}/next-actions") {
        post {
            val id: UUID by call.parameters
            val request = call.receive<CreateScheduledActionRequest>()
            createScheduledActionUseCase.handle(request.toCommand(id))
            call.respond(Created)
        }
    }
}
