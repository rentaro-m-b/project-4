package task.usecase

import com.example.task.entity.DueDate
import com.example.task.entity.Task
import com.example.task.entity.TaskDefinition
import com.example.task.entity.TaskDefinitionFactory
import com.example.task.entity.TaskDefinitionRepository
import com.example.task.entity.TaskFactory
import com.example.task.entity.TaskRepository
import com.example.task.usecase.CreateTaskDefinition
import com.example.task.usecase.CreateTaskDefinitionCommand
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
import java.time.Clock
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.UUID

class CreateTaskDefinitionUseCaseTest :
    FunSpec(),
    KoinTest {
    init {
        val target by inject<CreateTaskDefinition>()
        val taskDefinitionRepository = mockk<TaskDefinitionRepository>()
        val taskRepository = mockk<TaskRepository>()
        val taskDefinitionFactory = mockk<TaskDefinitionFactory>()
        val taskFactory = mockk<TaskFactory>()
        val clock = mockk<Clock>()

        beforeSpec {
            startKoin {
                modules(
                    module {
                        single { taskDefinitionRepository }
                        single { taskRepository }
                        single { taskDefinitionFactory }
                        single { taskFactory }
                        single { clock }
                        singleOf(::CreateTaskDefinition)
                    },
                )
            }
        }

        afterSpec {
            stopKoin()
        }

        test("normal: create task ticket definition") {
            // arrange
            every { clock.instant() } returns Instant.parse("2025-09-17T09:00:00Z")
            every { clock.zone } returns ZoneId.of("UTC")

            every {
                taskDefinitionFactory.create(
                    description = "「テスト駆動開発」を読む",
                    expected = BigDecimal("5"),
                    unit = "page",
                    cyclePerDays = 1,
                )
            } returns
                TaskDefinition(
                    id = UUID.fromString("3ee1f358-690f-4ac7-8eec-8e3be49419df"),
                    description = "「テスト駆動開発」を読む",
                    expected = BigDecimal("5"),
                    unit = "page",
                    cyclePerDays = 1,
                    createdAt = LocalDateTime.parse("2025-09-17T09:00:00"),
                    updatedAt = LocalDateTime.parse("2025-09-17T09:00:00"),
                )

            every {
                taskDefinitionRepository.create(
                    TaskDefinition(
                        id = UUID.fromString("3ee1f358-690f-4ac7-8eec-8e3be49419df"),
                        description = "「テスト駆動開発」を読む",
                        expected = BigDecimal("5"),
                        unit = "page",
                        cyclePerDays = 1,
                        createdAt = LocalDateTime.parse("2025-09-17T09:00:00"),
                        updatedAt = LocalDateTime.parse("2025-09-17T09:00:00"),
                    ),
                )
            } just Runs

            every {
                taskFactory.create(
                    TaskDefinition(
                        id = UUID.fromString("3ee1f358-690f-4ac7-8eec-8e3be49419df"),
                        description = "「テスト駆動開発」を読む",
                        expected = BigDecimal("5"),
                        unit = "page",
                        cyclePerDays = 1,
                        createdAt = LocalDateTime.parse("2025-09-17T09:00:00"),
                        updatedAt = LocalDateTime.parse("2025-09-17T09:00:00"),
                    ),
                    dueDate = DueDate(LocalDateTime.parse("2025-09-17T09:00:00")),
                )
            } returns
                Task(
                    id = UUID.fromString("3ee1f358-690f-4ac7-8eec-8e3be49419df"),
                    taskDefinitionId = UUID.fromString("3ee1f358-690f-4ac7-8eec-8e3be49419df"),
                    actual = BigDecimal(0),
                    dueDate = DueDate(LocalDateTime.parse("2025-09-17T09:00:00")),
                    createdAt = LocalDateTime.parse("2025-09-17T09:00:00"),
                    updatedAt = LocalDateTime.parse("2025-09-17T09:00:00"),
                )

            every {
                taskRepository.create(
                    Task(
                        id = UUID.fromString("3ee1f358-690f-4ac7-8eec-8e3be49419df"),
                        taskDefinitionId = UUID.fromString("3ee1f358-690f-4ac7-8eec-8e3be49419df"),
                        actual = BigDecimal(0),
                        dueDate = DueDate(LocalDateTime.parse("2025-09-17T09:00:00")),
                        createdAt = LocalDateTime.parse("2025-09-17T09:00:00"),
                        updatedAt = LocalDateTime.parse("2025-09-17T09:00:00"),
                    ),
                )
            } just Runs

            // act
            val actual =
                target.execute(
                    CreateTaskDefinitionCommand(
                        description = "「テスト駆動開発」を読む",
                        expected = BigDecimal("5"),
                        unit = "page",
                        cyclePerDays = 1,
                    ),
                )

            // assert
            actual shouldBe UUID.fromString("3ee1f358-690f-4ac7-8eec-8e3be49419df")
        }
    }
}
