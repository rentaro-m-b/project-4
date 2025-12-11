package com.example.controller.nextaction

import com.example.controller.nextaction.ListNextActionsResponse
import com.example.usecase.nextaction.CreateNextActionUseCase
import com.example.usecase.nextaction.DeleteNextActionCommand
import com.example.usecase.nextaction.DeleteNextActionUseCase
import com.example.usecase.nextaction.ListNextActionsUseCase
import com.example.usecase.nextaction.UpdateNextActionUseCase
import io.ktor.http.HttpStatusCode.Companion.Created
import io.ktor.http.HttpStatusCode.Companion.NoContent
import io.ktor.http.HttpStatusCode.Companion.OK
import io.ktor.http.content.OutgoingContent
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.util.*
import org.koin.ktor.ext.inject
import java.util.*

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
                updateNextActionUseCase.handle(request.toCommand(id))
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
            createNextActionUseCase.handle(request.toCommand(id))
            call.respond(Created)
        }
    }
}
