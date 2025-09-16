package com.example.common

import com.example.DBSettings
import com.example.common.ClockProvider.provideUtcClock
import com.example.common.DSLContextProvider.provideDSLContext
import com.example.common.DataSourceProvider.provideDataSource
import org.jooq.DSLContext
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import java.time.Clock
import javax.sql.DataSource

object CommonDIContainer {
    fun defineModule(settings: DBSettings): Module =
        module {
            single { settings } bind DBSettings::class
            singleOf(::provideUtcClock) bind Clock::class
            singleOf(::provideDataSource) bind DataSource::class
            singleOf(::provideDSLContext) bind DSLContext::class
        }
}
