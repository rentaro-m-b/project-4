package task.factory

import com.example.task.entity.DueDate
import com.example.task.entity.Task
import com.example.task.entity.TaskDefinition
import com.example.task.entity.TaskFactory
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.equality.shouldBeEqualToIgnoringFields
import io.mockk.every
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

class TaskFactoryTest :
    FunSpec(),
    KoinTest {
    init {
        val target by inject<TaskFactory>()
        val clock = mockk<Clock>()

        beforeSpec {
            startKoin {
                modules(
                    module {
                        single { clock }
                        singleOf(::TaskFactory)
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

            // act
            val actual =
                target.create(
                    taskDefinition =
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

            // assert
            val expected =
                Task(
                    id = UUID.randomUUID(),
                    taskDefinitionId = UUID.fromString("3ee1f358-690f-4ac7-8eec-8e3be49419df"),
                    actual = BigDecimal(0),
                    dueDate = DueDate(LocalDateTime.parse("2025-09-17T09:00:00")),
                    createdAt = LocalDateTime.parse("2025-09-17T09:00:00"),
                    updatedAt = LocalDateTime.parse("2025-09-17T09:00:00"),
                )
            actual.shouldBeEqualToIgnoringFields(expected, TaskDefinition::id)
        }
    }
}
