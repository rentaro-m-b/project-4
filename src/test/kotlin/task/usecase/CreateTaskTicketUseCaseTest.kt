package task.usecase

import com.example.task.entity.TaskTicketDefinition
import com.example.task.entity.TaskTicketDefinitionFactory
import com.example.task.entity.TaskTicketDefinitionRepository
import com.example.task.usecase.CreateTaskTicketDefinitionCommand
import com.example.task.usecase.CreateTaskTicketDefinitionUseCase
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.inject
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.UUID

class CreateTaskTicketUseCaseTest :
    FunSpec(),
    KoinTest {
    init {
        val target by inject<CreateTaskTicketDefinitionUseCase>()
        val repository = mockk<TaskTicketDefinitionRepository>()
        val factory = mockk<TaskTicketDefinitionFactory>()

        beforeSpec {
            startKoin {
                modules(
                    module {
                        single { repository }
                        single { factory }
                        singleOf(::CreateTaskTicketDefinitionUseCase)
                    },
                )
            }
        }

        afterSpec {
            stopKoin()
        }

        test("normal: create task ticket definition") {
            // arrange
            every {
                factory.create(
                    description = "「テスト駆動開発」を読む",
                    expected = BigDecimal("5"),
                    unit = "page",
                )
            } returns
                TaskTicketDefinition(
                    id = UUID.fromString("3ee1f358-690f-4ac7-8eec-8e3be49419df"),
                    description = "「テスト駆動開発」を読む",
                    expected = BigDecimal("5"),
                    unit = "page",
                    createdAt = LocalDateTime.parse("2025-09-17T09:00:00"),
                    updatedAt = LocalDateTime.parse("2025-09-17T09:00:00"),
                )

            every {
                repository.create(
                    TaskTicketDefinition(
                        id = UUID.fromString("3ee1f358-690f-4ac7-8eec-8e3be49419df"),
                        description = "「テスト駆動開発」を読む",
                        expected = BigDecimal("5"),
                        unit = "page",
                        createdAt = LocalDateTime.parse("2025-09-17T09:00:00"),
                        updatedAt = LocalDateTime.parse("2025-09-17T09:00:00"),
                    ),
                )
            } just Runs

            // act
            val actual =
                target.execute(
                    CreateTaskTicketDefinitionCommand(
                        description = "「テスト駆動開発」を読む",
                        expected = BigDecimal("5"),
                        unit = "page",
                    ),
                )

            // assert
            actual shouldBe UUID.fromString("3ee1f358-690f-4ac7-8eec-8e3be49419df")
        }
    }
}
