package task.factory

import com.example.task.entity.TaskTicketDefinition
import com.example.task.entity.TaskTicketDefinitionFactory
import io.kotest.core.spec.style.FunSpec
import io.kotest.koin.KoinExtension
import io.kotest.matchers.equality.shouldBeEqualToIgnoringFields
import io.mockk.every
import io.mockk.mockk
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

class TaskTicketDefinitionFactoryTest :
    FunSpec(),
    KoinTest {
    init {
        val clock = mockk<Clock>()

        val testModule =
            module {
                single { clock }
                singleOf(::TaskTicketDefinitionFactory)
            }
        extensions(KoinExtension(testModule))

        val target by inject<TaskTicketDefinitionFactory>()

        test("normal: create task ticket definition") {
            // arrange
            every { clock.instant() } returns Instant.parse("2025-09-17T09:00:00Z")
            every { clock.zone } returns ZoneId.of("UTC")

            // act
            val actual =
                target.create(
                    description = "「テスト駆動開発」を読む",
                    expected = BigDecimal("5"),
                    unit = "page",
                )

            // assert
            val expected =
                TaskTicketDefinition(
                    id = UUID.randomUUID(),
                    description = "「テスト駆動開発」を読む",
                    expected = BigDecimal("5"),
                    unit = "page",
                    createdAt = LocalDateTime.parse("2025-09-17T09:00:00"),
                    updatedAt = LocalDateTime.parse("2025-09-17T09:00:00"),
                )
            actual.shouldBeEqualToIgnoringFields(expected, TaskTicketDefinition::id)
        }
    }
}
