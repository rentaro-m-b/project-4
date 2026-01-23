package com.example

import com.example.infra.DataSource
import io.ktor.server.application.Application
import io.ktor.server.application.install
import org.jooq.SQLDialect
import org.jooq.impl.DSL
import org.koin.dsl.includes
import org.koin.dsl.module
import org.koin.ksp.generated.koinConfiguration
import org.koin.ktor.plugin.Koin

fun Application.configureDependencyInjection() {
    install(Koin) {
        modules(
            module {
                single { DSL.using(DataSource(environment.config).getConnection(), SQLDialect.POSTGRES) }
            },
        )
        includes(KoinApplication.koinConfiguration())
    }
}
