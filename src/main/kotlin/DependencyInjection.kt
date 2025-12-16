package com.example

import com.example.domain.nextaction.NextActionRepository
import com.example.domain.shceduledaction.ScheduledActionRepository
import com.example.domain.stickynote.StickyNoteRepository
import com.example.infra.DataSource
import com.example.infra.nextaction.NextActionRepositoryImpl
import com.example.infra.scheduledaction.ScheduledActionRepositoryImpl
import com.example.infra.stickynote.StickyNoteRepositoryImpl
import com.example.usecase.nextaction.CreateNextActionUseCase
import com.example.usecase.nextaction.DeleteNextActionUseCase
import com.example.usecase.nextaction.ListNextActionsUseCase
import com.example.usecase.nextaction.UpdateNextActionUseCase
import com.example.usecase.scheduledaction.CreateScheduledActionUseCase
import com.example.usecase.scheduledaction.DeleteScheduledActionUseCase
import com.example.usecase.scheduledaction.ListScheduledActionsUseCase
import com.example.usecase.scheduledaction.UpdateScheduledActionUseCase
import com.example.usecase.stickynote.CreateStickyNoteUseCase
import com.example.usecase.stickynote.DeleteStickyNoteUseCase
import com.example.usecase.stickynote.ListStickyNotesUseCase
import com.example.usecase.stickynote.UpdateStickyNoteUseCase
import io.ktor.server.application.Application
import io.ktor.server.application.install
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
                singleOf(::ScheduledActionRepositoryImpl) { bind<ScheduledActionRepository>() }
                singleOf(::ListStickyNotesUseCase)
                singleOf(::CreateStickyNoteUseCase)
                singleOf(::UpdateStickyNoteUseCase)
                singleOf(::DeleteStickyNoteUseCase)
                singleOf(::ListNextActionsUseCase)
                singleOf(::CreateNextActionUseCase)
                singleOf(::UpdateNextActionUseCase)
                singleOf(::DeleteNextActionUseCase)
                singleOf(::ListScheduledActionsUseCase)
                singleOf(::CreateScheduledActionUseCase)
                singleOf(::UpdateScheduledActionUseCase)
                singleOf(::DeleteScheduledActionUseCase)
            },
        )
    }
}
