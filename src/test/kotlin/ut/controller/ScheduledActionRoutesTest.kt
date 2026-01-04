package ut.controller

import com.example.configureRouting
import com.example.configureSerialization
import com.example.domain.shceduledaction.ScheduledAction
import com.example.usecase.scheduledaction.ListScheduledActionsUseCase
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.HttpStatusCode.Companion.OK
import io.ktor.server.application.install
import io.ktor.server.testing.testApplication
import io.mockk.every
import io.mockk.mockk
import org.koin.dsl.module
import org.koin.ktor.plugin.Koin
import java.time.LocalDateTime
import java.util.UUID
import kotlin.test.Test
import kotlin.test.assertEquals

class ScheduledActionRoutesTest {
    @Test
    fun listScheduledActions() =
        testApplication {
            // setup
            val listScheduledActionsUseCase = mockk<ListScheduledActionsUseCase>()
            application {
                configureSerialization()
                configureRouting()
                install(Koin) {
                    modules(
                        module {
                            single { listScheduledActionsUseCase }
                        },
                    )
                }
            }

            every { listScheduledActionsUseCase.handle() } returns
                listOf(
                    ScheduledAction.create(
                        id = UUID.fromString("af5307cb-5147-46ad-affa-92be36a67645"),
                        description = "practice drawing for 10 minutes",
                        startsAt = LocalDateTime.parse("2025-02-01T12:00:00"),
                        endsAt = LocalDateTime.parse("2025-02-01T13:00:00"),
                        createdAt = LocalDateTime.parse("2025-01-01T00:00:00"),
                    ),
                    ScheduledAction.create(
                        id = UUID.fromString("63bc709a-6d1c-4e80-8639-755f48e284d6"),
                        description = "collect five reference materials",
                        startsAt = LocalDateTime.parse("2025-02-01T12:00:01"),
                        endsAt = LocalDateTime.parse("2025-02-01T13:00:01"),
                        createdAt = LocalDateTime.parse("2025-01-01T00:00:01"),
                    ),
                    ScheduledAction.create(
                        id = UUID.fromString("da48c76d-1ae3-44d1-86b6-dc2b6206b761"),
                        description = "decide on a theme for the painting",
                        startsAt = LocalDateTime.parse("2025-02-01T12:00:02"),
                        endsAt = LocalDateTime.parse("2025-02-01T13:00:02"),
                        createdAt = LocalDateTime.parse("2025-01-01T00:00:02"),
                    ),
                    ScheduledAction.create(
                        id = UUID.fromString("83f4b2cb-f383-4ef0-983e-3c5ca57ae809"),
                        description = "decide which contest to submit to",
                        startsAt = LocalDateTime.parse("2025-02-01T12:00:03"),
                        endsAt = LocalDateTime.parse("2025-02-01T13:00:03"),
                        createdAt = LocalDateTime.parse("2025-01-01T00:00:03"),
                    ),
                )

            // execute
            val response = client.get("/scheduled-actions")

            // assert
            val expected =
                formatAsExpected(
                    """
                    [
                        {"description":"practice drawing for 10 minutes","startsAt":"2025-02-01T12:00:00","endsAt":"2025-02-01T13:00:00","createdAt":"2025-01-01T00:00:00"},
                        {"description":"collect five reference materials","startsAt":"2025-02-01T12:00:01","endsAt":"2025-02-01T13:00:01","createdAt":"2025-01-01T00:00:01"},
                        {"description":"decide on a theme for the painting","startsAt":"2025-02-01T12:00:02","endsAt":"2025-02-01T13:00:02","createdAt":"2025-01-01T00:00:02"},
                        {"description":"decide which contest to submit to","startsAt":"2025-02-01T12:00:03","endsAt":"2025-02-01T13:00:03","createdAt":"2025-01-01T00:00:03"}
                    ]
                """,
                )
            assertEquals(OK, response.status)
            assertEquals(expected, response.body())
        }

    private fun formatAsExpected(preExpected: String): String =
        preExpected
            .lineSequence()
            .map { it.trim() }
            .joinToString("")
}
