import com.example.LocalDateTimeSerializer
import com.example.controller.common.ErrorResponse
import com.example.controller.scheduledaction.CreateScheduledActionRequest
import com.example.controller.scheduledaction.UpdateScheduledActionRequest
import com.example.module
import com.github.database.rider.core.api.dataset.DataSet
import com.github.database.rider.core.api.dataset.ExpectedDataSet
import com.github.database.rider.junit5.api.DBRider
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode.Companion.Created
import io.ktor.http.HttpStatusCode.Companion.NoContent
import io.ktor.http.HttpStatusCode.Companion.NotFound
import io.ktor.http.HttpStatusCode.Companion.OK
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.config.ApplicationConfig
import io.ktor.server.testing.testApplication
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import org.junit.jupiter.api.Test
import java.time.LocalDateTime
import kotlin.test.assertEquals

@DBRider
class ScheduledActionRoutesTest {
    @Test
    @DataSet(value = ["datasets/setup/scheduledActions.yaml"], cleanBefore = true)
    fun listScheduledActions() =
        testApplication {
            // setup
            environment {
                config = ApplicationConfig("application.yaml")
            }
            application {
                module()
            }

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

    @Test
    @DataSet(
        value = [
            "datasets/setup/scheduledActions.yaml",
            "datasets/setup/stickyNotes.yaml",
        ],
        cleanBefore = true,
    )
    @ExpectedDataSet(
        value = ["datasets/expected/scheduledaction/createScheduledAction.yaml"],
        orderBy = ["created_at"],
    )
    fun createScheduledAction() =
        testApplication {
            // setup
            environment {
                config = ApplicationConfig("application.yaml")
            }
            application {
                module()
            }

            // execute
            val client =
                createClient {
                    install(ContentNegotiation) {
                        json(
                            Json {
                                serializersModule =
                                    SerializersModule {
                                        contextual(LocalDateTime::class, LocalDateTimeSerializer)
                                    }
                            },
                        )
                    }
                }
            val actual =
                client.post("/sticky-notes/ae95e722-253d-4fde-94f7-598da746cf0c/scheduled-actions") {
                    header(
                        HttpHeaders.ContentType,
                        ContentType.Application.Json,
                    )
                    setBody(
                        CreateScheduledActionRequest(
                            description = "draw for 10 minutes",
                            startsAt = LocalDateTime.parse("2025-03-01T12:00:00"),
                            endsAt = LocalDateTime.parse("2025-03-01T13:00:00"),
                        ),
                    )
                }

            // assert
            assertEquals(Created, actual.status)
        }

    @Test
    @DataSet(
        value = [
            "datasets/setup/scheduledActions.yaml",
            "datasets/setup/stickyNotes.yaml",
        ],
        cleanBefore = true,
    )
    @ExpectedDataSet(
        value = ["datasets/setup/scheduledActions.yaml"],
        orderBy = ["created_at"],
    )
    fun createScheduledAction_notFoundStickyNote() =
        testApplication {
            // setup
            environment {
                config = ApplicationConfig("application.yaml")
            }
            application {
                module()
            }

            // execute
            val client =
                createClient {
                    install(ContentNegotiation) {
                        json(
                            Json {
                                serializersModule =
                                    SerializersModule {
                                        contextual(LocalDateTime::class, LocalDateTimeSerializer)
                                    }
                            },
                        )
                    }
                }
            val actual =
                client.post("/sticky-notes/f3196969-a4c5-4483-bd10-5b92e58d5ef8/scheduled-actions") {
                    header(
                        HttpHeaders.ContentType,
                        ContentType.Application.Json,
                    )
                    setBody(
                        CreateScheduledActionRequest(
                            description = "draw for 10 minutes",
                            startsAt = LocalDateTime.parse("2025-03-01T12:00:00"),
                            endsAt = LocalDateTime.parse("2025-03-01T13:00:00"),
                        ),
                    )
                }

            // assert
            val expected =
                ErrorResponse(
                    type = "blanck",
                    title = "Not found sticky note.",
                    detail = "No sticky note matching the id was found.",
                    instance = "/sticky-notes/f3196969-a4c5-4483-bd10-5b92e58d5ef8/scheduled-actions",
                )
            assertEquals(NotFound, actual.status)
            assertEquals(expected, actual.body())
        }

    @Test
    @DataSet(value = ["datasets/setup/scheduledActions.yaml"], cleanBefore = true)
    @ExpectedDataSet(
        value = ["datasets/expected/scheduledaction/updateScheduledAction.yaml"],
        orderBy = ["created_at"],
    )
    fun updateScheduledAction() =
        testApplication {
            // setup
            environment {
                config = ApplicationConfig("application.yaml")
            }
            application {
                module()
            }

            // execute
            val client =
                createClient {
                    install(ContentNegotiation) {
                        json(
                            Json {
                                serializersModule =
                                    SerializersModule {
                                        contextual(LocalDateTime::class, LocalDateTimeSerializer)
                                    }
                            },
                        )
                    }
                }
            val actual =
                client.put("/scheduled-actions/af5307cb-5147-46ad-affa-92be36a67645") {
                    header(
                        HttpHeaders.ContentType,
                        ContentType.Application.Json,
                    )
                    setBody(
                        UpdateScheduledActionRequest(
                            description = "practice drawing for 7 minutes",
                            startsAt = LocalDateTime.parse("2025-02-01T13:00:00"),
                            endsAt = LocalDateTime.parse("2025-02-01T13:30:00"),
                        ),
                    )
                }

            // assert
            assertEquals(NoContent, actual.status)
        }

    @Test
    @DataSet(value = ["datasets/setup/scheduledActions.yaml"], cleanBefore = true)
    @ExpectedDataSet(
        value = ["datasets/setup/scheduledActions.yaml"],
        orderBy = ["created_at"],
    )
    fun updateScheduledAction_notFound() =
        testApplication {
            // setup
            environment {
                config = ApplicationConfig("application.yaml")
            }
            application {
                module()
            }

            // execute
            val client =
                createClient {
                    install(ContentNegotiation) {
                        json(
                            Json {
                                serializersModule =
                                    SerializersModule {
                                        contextual(LocalDateTime::class, LocalDateTimeSerializer)
                                    }
                            },
                        )
                    }
                }
            val actual =
                client.put("/scheduled-actions/8eb01866-816a-4734-b793-d8c455e9af3a") {
                    header(
                        HttpHeaders.ContentType,
                        ContentType.Application.Json,
                    )
                    setBody(
                        UpdateScheduledActionRequest(
                            description = "practice drawing for 7 minutes",
                            startsAt = LocalDateTime.parse("2025-02-01T13:00:00"),
                            endsAt = LocalDateTime.parse("2025-02-01T13:30:00"),
                        ),
                    )
                }

            // assert
            val expected =
                ErrorResponse(
                    type = "blanck",
                    title = "Not found next action.",
                    detail = "No next action matching the id was found.",
                    instance = "/next-actions/8eb01866-816a-4734-b793-d8c455e9af3a",
                )
            assertEquals(NotFound, actual.status)
            assertEquals(expected, actual.body())
        }

    @Test
    @DataSet(value = ["datasets/setup/scheduledActions.yaml"], cleanBefore = true)
    @ExpectedDataSet(
        value = ["datasets/expected/scheduledaction/deleteScheduledAction.yaml"],
        orderBy = ["created_at"],
    )
    fun deleteStickyNote() =
        testApplication {
            // setup
            environment {
                config = ApplicationConfig("application.yaml")
            }
            application {
                module()
            }

            // execute
            val client =
                createClient {
                    install(ContentNegotiation) {
                        json()
                    }
                }
            val actual = client.delete("/scheduled-actions/af5307cb-5147-46ad-affa-92be36a67645")

            // assert
            assertEquals(NoContent, actual.status)
        }

    private fun formatAsExpected(preExpected: String): String =
        preExpected
            .lineSequence()
            .map { it.trim() }
            .joinToString("")
}
