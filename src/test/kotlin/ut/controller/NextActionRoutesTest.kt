package ut.controller

import com.example.configureRouting
import com.example.configureSerialization
import com.example.controller.common.ErrorResponse
import com.example.controller.nextaction.dto.CreateNextActionRequest
import com.example.domain.nextaction.NextAction
import com.example.module
import com.example.usecase.common.CurrentStickyNoteNotFoundException
import com.example.usecase.nextaction.CreateNextActionCommand
import com.example.usecase.nextaction.CreateNextActionUseCase
import com.example.usecase.nextaction.ListNextActionsUseCase
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode.Companion.Created
import io.ktor.http.HttpStatusCode.Companion.NotFound
import io.ktor.http.HttpStatusCode.Companion.OK
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.install
import io.ktor.server.config.ApplicationConfig
import io.ktor.server.testing.testApplication
import io.mockk.every
import io.mockk.mockk
import org.koin.dsl.module
import org.koin.ktor.plugin.Koin
import java.time.LocalDateTime
import java.util.*
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

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

    @Test
    fun listNextActionsNoContents() =
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

            every { listNextActionsUseCase.handle() } returns listOf()

            // execute
            val response = client.get("/next-actions")

            // assert
            val expected = formatAsExpected("[]")
            assertEquals(OK, response.status)
            assertEquals(expected, response.body())
        }

    @Test
    fun createNextAction() =
        testApplication {
            // setup
            val createNextActionsUseCase = mockk<CreateNextActionUseCase>()
            application {
                configureSerialization()
                configureRouting()
                install(Koin) {
                    modules(
                        module {
                            single { createNextActionsUseCase }
                        },
                    )
                }
            }

            every {
                createNextActionsUseCase
                    .handle(
                        CreateNextActionCommand(
                            stickyNoteId = UUID.fromString("ae95e722-253d-4fde-94f7-598da746cf0c"),
                            description = "draw for 10 minutes",
                        ),
                    )
            } returns Result.success(UUID.fromString("d4849eb6-8736-4e09-8061-0e0271d38a88"))

            // execute
            val client =
                createClient {
                    install(ContentNegotiation) {
                        json()
                    }
                }
            val actual =
                client.post("/sticky-notes/ae95e722-253d-4fde-94f7-598da746cf0c/next-actions") {
                    header(
                        HttpHeaders.ContentType,
                        ContentType.Application.Json,
                    )
                    setBody(CreateNextActionRequest("draw for 10 minutes"))
                }

            // assert
            assertEquals(Created, actual.status)
            assertTrue(actual.headers["Location"]!!.startsWith("/next-actions/"))
        }

    @Test
    fun createNextActionNotFound() =
        testApplication {
            // setup
            val createNextActionsUseCase = mockk<CreateNextActionUseCase>()
            application {
                configureSerialization()
                configureRouting()
                install(Koin) {
                    modules(
                        module {
                            single { createNextActionsUseCase }
                        },
                    )
                }
            }

            every {
                createNextActionsUseCase
                    .handle(
                        CreateNextActionCommand(
                            stickyNoteId = UUID.fromString("16adefdc-bc0f-4a7e-851a-865cd6261386"),
                            description = "draw for 10 minutes",
                        ),
                    )
            } returns Result.failure(CurrentStickyNoteNotFoundException("sticky note not found : 16adefdc-bc0f-4a7e-851a-865cd6261386"))

            // execute
            val client =
                createClient {
                    install(ContentNegotiation) {
                        json()
                    }
                }
            val actual =
                client.post("/sticky-notes/16adefdc-bc0f-4a7e-851a-865cd6261386/next-actions") {
                    header(
                        HttpHeaders.ContentType,
                        ContentType.Application.Json,
                    )
                    setBody(CreateNextActionRequest("draw for 10 minutes"))
                }

            // assert
            val expected =
                ErrorResponse(
                    type = "blanck",
                    title = "Not found sticky note.",
                    detail = "No sticky note matching the id was found.",
                    instance = "/sticky-notes/16adefdc-bc0f-4a7e-851a-865cd6261386/next-actions",
                )
            assertEquals(NotFound, actual.status)
            assertEquals(expected, actual.body())
        }

    private fun formatAsExpected(preExpected: String): String =
        preExpected
            .lineSequence()
            .map { it.trim() }
            .joinToString("")
}
