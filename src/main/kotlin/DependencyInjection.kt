package com.example

import com.example.domain.nextaction.NextActionRepository
import com.example.domain.stickynote.StickyNoteRepository
import com.example.infra.DataSource
import com.example.infra.nextaction.NextActionRepositoryImpl
import com.example.infra.stickynote.StickyNoteRepositoryImpl
import com.example.usecase.nextaction.CreateNextActionUseCase
import com.example.usecase.stickynote.CreateStickyNoteUseCase
import com.example.usecase.stickynote.DeleteStickyNoteUseCase
import com.example.usecase.stickynote.ListStickyNotesUseCase
import com.example.usecase.stickynote.UpdateStickyNoteUseCase
import io.ktor.server.application.*
import org.jooq.SQLDialect
import org.jooq.impl.DSL
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import org.koin.ktor.plugin.Koin

fun Application.configureDependencyInjection() {
    install(Koin) {
        modules(
            module {
                single { DSL.using(DataSource(environment.config).getConnection(), SQLDialect.POSTGRES) }
                singleOf(::StickyNoteRepositoryImpl) { bind<StickyNoteRepository>() }
                singleOf(::NextActionRepositoryImpl) { bind<NextActionRepository>() }
                singleOf(::ListStickyNotesUseCase)
                singleOf(::CreateStickyNoteUseCase)
                singleOf(::UpdateStickyNoteUseCase)
                singleOf(::DeleteStickyNoteUseCase)
                singleOf(::CreateNextActionUseCase)
            }
        )
    }
}
