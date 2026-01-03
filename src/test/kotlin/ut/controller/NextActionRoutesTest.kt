package ut.controller

import com.example.configureRouting
import com.example.configureSerialization
import com.example.domain.nextaction.NextAction
import com.example.usecase.nextaction.ListNextActionsUseCase
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

class NextActionRoutesTest {
    @Test
    fun listNextActions() =
        testApplication {
            // setup
            val listNextActionsUseCase = mockk<ListNextActionsUseCase>()
            application {
                configureSerialization()
                configureRouting()
                install(Koin) {
                    modules(
                        module {
                            single { listNextActionsUseCase }
                        },
                    )
                }
            }

            every { listNextActionsUseCase.handle() } returns
                listOf(
                    NextAction.create(
                        id = UUID.fromString("e574a515-5170-4d76-afc9-da17513dc5d3"),
                        description = "practice drawing for 10 minutes",
                        createdAt = LocalDateTime.parse("2025-01-01T00:00:00"),
                    ),
                    NextAction.create(
                        id = UUID.fromString("0909127e-69dc-4f7a-82b2-9da21e2cd090"),
                        description = "collect five reference materials",
                        createdAt = LocalDateTime.parse("2025-01-01T00:00:01"),
                    ),
                    NextAction.create(
                        id = UUID.fromString("117f0003-9f42-4750-9ed2-9356bb8a9887"),
                        description = "decide on a theme for the painting",
                        createdAt = LocalDateTime.parse("2025-01-01T00:00:02"),
                    ),
                    NextAction.create(
                        id = UUID.fromString("bc562522-7dc8-42bd-a0ee-6cc5ef238e9f"),
                        description = "decide which contest to submit to",
                        createdAt = LocalDateTime.parse("2025-01-01T00:00:03"),
                    ),
                )

            // execute
            val response = client.get("/next-actions")

            // assert
            val expected =
                formatAsExpected(
                    """
                    [
                        {"description":"practice drawing for 10 minutes","createdAt":"2025-01-01T00:00:00"},
                        {"description":"collect five reference materials","createdAt":"2025-01-01T00:00:01"},
                        {"description":"decide on a theme for the painting","createdAt":"2025-01-01T00:00:02"},
                        {"description":"decide which contest to submit to","createdAt":"2025-01-01T00:00:03"}
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
