package com.example

import com.example.controller.stickynote.stickyNoteRoutes
import com.example.domain.stickynote.StickyNoteRepository
import com.example.infra.stickynote.StickyNoteRepositoryImpl
import com.example.usecase.stickynote.CreateStickyNoteUseCase
import com.example.usecase.stickynote.DeleteStickyNoteUseCase
import com.example.usecase.stickynote.ListStickyNotesUseCase
import com.example.usecase.stickynote.UpdateStickyNoteUseCase
import io.ktor.http.HttpStatusCode.Companion.OK
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import org.koin.ktor.plugin.Koin

fun Application.configureRouting() {
    install(Koin) {
        modules(
            module {
                single { configureDataSource() }
                singleOf(::StickyNoteRepositoryImpl) { bind<StickyNoteRepository>() }
                singleOf(::ListStickyNotesUseCase)
                singleOf(::CreateStickyNoteUseCase)
                singleOf(::UpdateStickyNoteUseCase)
                singleOf(::DeleteStickyNoteUseCase)
            }
        )
    }

    routing {
        get("/health") {
            call.response.status(OK)
            call.respond("Hello, World!")
        }
        stickyNoteRoutes()
    }
}
