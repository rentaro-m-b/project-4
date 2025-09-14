package com.example.common

import io.ktor.server.application.Application
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.ktor.plugin.koinModule
import java.time.Clock

fun Application.commonModule() {
    fun provideUtcClock(): Clock = Clock.systemUTC()

    koinModule {
        singleOf(::provideUtcClock) bind Clock::class
    }
}
