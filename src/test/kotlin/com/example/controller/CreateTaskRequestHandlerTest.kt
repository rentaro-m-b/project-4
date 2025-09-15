package com.example.controller

import com.example.TestConfig.provideDataSource
import com.example.TestConfig.provideDslContext
import com.example.task.controller.CreateTaskRequest
import com.example.task.controller.CreateTaskRequestHandler
import com.example.task.dataaccess.TaskTicketDefinitionRepositoryImpl
import com.example.task.entity.TaskTicketDefinitionFactory
import com.example.task.entity.TaskTicketDefinitionRepository
import com.example.task.usecase.CreateTaskTicketDefinitionUseCase
import io.kotest.core.spec.style.FunSpec
import io.kotest.koin.KoinExtension
import io.kotest.matchers.shouldBe
import org.jooq.DSLContext
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.inject
import java.time.Clock
import javax.sql.DataSource

class CreateTaskRequestHandlerTest :
    FunSpec(),
    KoinTest {
    init {
        fun provideUtcClock(): Clock = Clock.systemUTC()

        val testModule =
            module {
                singleOf(::TaskTicketDefinitionRepositoryImpl) bind TaskTicketDefinitionRepository::class
                singleOf(::CreateTaskTicketDefinitionUseCase)
                singleOf(::TaskTicketDefinitionFactory)
                singleOf(::CreateTaskRequestHandler)
                singleOf(::provideDataSource) bind DataSource::class
                singleOf(::provideDslContext) bind DSLContext::class
                singleOf(::provideUtcClock) bind Clock::class
            }

        extensions(KoinExtension(modules = listOf(testModule)))

        val target by inject<CreateTaskRequestHandler>()

        test("normal: create task ticket definition") {
            val id =
                target.handle(
                    request =
                        CreateTaskRequest(
                            description = "「テスト駆動開発」を読む",
                            expected = "5",
                            unit = "page",
                        ),
                )
            id.toString().isNotBlank() shouldBe false
        }
    }
}
