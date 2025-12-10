package com.example.controller.nextaction

import com.example.usecase.nextaction.CreateNextActionUseCase
import io.ktor.http.HttpStatusCode.Companion.Created
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.util.*
import org.koin.ktor.ext.inject
import java.util.*

fun Route.nextActionRoutes() {
    val createNextActionUseCase by inject<CreateNextActionUseCase>()

    route("/sticky-notes/{id}/next-actions") {
        post {
            val id: UUID by call.parameters
            val request = call.receive<CreateNextActionRequest>()
            createNextActionUseCase.handle(request.toCommand(id))
            call.respond(Created)
        }
    }
}
