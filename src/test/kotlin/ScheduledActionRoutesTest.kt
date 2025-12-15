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
import io.ktor.http.HttpStatusCode.Companion.OK
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.config.ApplicationConfig
import io.ktor.server.testing.testApplication
import org.junit.jupiter.api.Test
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
                        json()
                    }
                }
            val actual =
                client.post("/sticky-notes/ae95e722-253d-4fde-94f7-598da746cf0c/scheduled-actions") {
                    header(
                        HttpHeaders.ContentType,
                        ContentType.Application.Json,
                    )
                    setBody(CreateScheduledActionRequest("draw for 10 minutes"))
                }

            // assert
            assertEquals(Created, actual.status)
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
                        json()
                    }
                }
            val actual =
                client.put("/scheduled-actions/e574a515-5170-4d76-afc9-da17513dc5d3") {
                    header(
                        HttpHeaders.ContentType,
                        ContentType.Application.Json,
                    )
                    setBody(UpdateScheduledActionRequest("practice drawing for 7 minutes"))
                }

            // assert
            assertEquals(NoContent, actual.status)
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
            val actual = client.delete("/scheduled-actions/e574a515-5170-4d76-afc9-da17513dc5d3")

            // assert
            assertEquals(NoContent, actual.status)
        }

    private fun formatAsExpected(preExpected: String): String =
        preExpected
            .lineSequence()
            .map { it.trim() }
            .joinToString("")
}
