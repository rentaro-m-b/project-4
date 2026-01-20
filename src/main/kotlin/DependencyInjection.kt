package com.example

import com.example.domain.stickynote.StickyNoteRepository
import com.example.infra.DataSource
import com.example.infra.stickynote.StickyNoteRepositoryImpl
import com.example.usecase.stickynote.CreateStickyNoteUseCase
import com.example.usecase.stickynote.DeleteStickyNoteUseCase
import com.example.usecase.stickynote.IdGenerator
import com.example.usecase.stickynote.ListStickyNotesUseCase
import com.example.usecase.stickynote.UpdateStickyNoteUseCase
import io.ktor.server.application.Application
import io.ktor.server.application.install
import org.jooq.SQLDialect
import org.jooq.impl.DSL
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import org.koin.ktor.plugin.Koin
import org.koin.mp.KoinPlatform

// @Module
// @ComponentScan("com.example.usecase.stickynote") // IdGenerator がいるパッケージ
// class SampleModule

fun Application.configureDependencyInjection() {
    install(Koin) {
        modules(
            listOf(
//                SampleModule().module,
                module {
                    single { DSL.using(DataSource(environment.config).getConnection(), SQLDialect.POSTGRES) }
                    singleOf(::StickyNoteRepositoryImpl) { bind<StickyNoteRepository>() }
                    singleOf(::ListStickyNotesUseCase)
                    singleOf(::CreateStickyNoteUseCase)
                    singleOf(::UpdateStickyNoteUseCase)
                    singleOf(::DeleteStickyNoteUseCase)
                },
            ),
        )
    }
}
