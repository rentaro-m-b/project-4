package task.dataaccess

import com.example.DBSettings
import com.example.common.ClockProvider.provideUtcClock
import com.example.common.DSLContextProvider.provideDSLContext
import com.example.common.DataSourceProvider.provideDataSource
import com.example.db.tables.TaskTicketDefinitions.Companion.TASK_TICKET_DEFINITIONS
import com.example.task.controller.CreateTaskRequest
import com.example.task.dataaccess.TaskTicketDefinitionRepositoryImpl
import com.example.task.entity.TaskTicketDefinition
import com.example.task.usecase.CreateTaskTicketDefinitionCommand
import io.kotest.core.spec.style.FunSpec
import io.kotest.koin.KoinExtension
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import org.jooq.DSLContext
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.inject
import java.math.BigDecimal
import java.time.Clock
import java.time.LocalDateTime
import java.util.UUID
import javax.sql.DataSource

class TaskTicketDefinitionRepositoryImplTest :
    FunSpec(),
    KoinTest {
    init {
        // TODO: wright db settings
        val settings =
            DBSettings(
                url = "",
                user = "root",
                password = "password",
                driver = "",
                maxPoolSize = 10,
            )
        val testModule =
            module {
                single { settings } bind DBSettings::class
                singleOf(::provideDataSource) bind DataSource::class
                singleOf(::provideDSLContext) bind DSLContext::class
                singleOf(::TaskTicketDefinitionRepositoryImpl)
            }
        extensions(KoinExtension(testModule))

        val target by inject<TaskTicketDefinitionRepositoryImpl>()

        test("normal: create task ticket definition") {
            // arrange

            // act
            val id =
                target.create(
                    entity =
                        TaskTicketDefinition(
                            id = UUID.fromString("3ee1f358-690f-4ac7-8eec-8e3be49419df"),
                            description = "「テスト駆動開発」を読む",
                            expected = BigDecimal("5"),
                            unit = "page",
                            createdAt = LocalDateTime.parse("2025-09-17T09:00:00"),
                            updatedAt = LocalDateTime.parse("2025-09-17T09:00:00"),
                        ),
                )

            // assert
            id shouldBe UUID.fromString("3ee1f358-690f-4ac7-8eec-8e3be49419df")
        }
    }
}
