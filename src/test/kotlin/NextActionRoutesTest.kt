import com.example.controller.nextaction.CreateNextActionRequest
import com.example.controller.nextaction.UpdateNextActionRequest
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
import org.junit.jupiter.api.Test
import org.zalando.problem.Problem
import org.zalando.problem.Status
import java.net.URI
import kotlin.test.assertEquals

@DBRider
class NextActionRoutesTest {
    @Test
    @DataSet(value = ["datasets/setup/nextActions.yaml"], cleanBefore = true)
    fun listNextActions() =
        testApplication {
            // setup
            environment {
                config = ApplicationConfig("application.yaml")
            }
            application {
                module()
            }

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
    @DataSet(
        value = [
            "datasets/setup/nextActions.yaml",
            "datasets/setup/stickyNotes.yaml",
        ],
        cleanBefore = true,
    )
    @ExpectedDataSet(
        value = ["datasets/expected/nextaction/createNextAction.yaml"],
        orderBy = ["created_at"],
    )
    fun createNextAction() =
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
                client.post("/sticky-notes/ae95e722-253d-4fde-94f7-598da746cf0c/next-actions") {
                    header(
                        HttpHeaders.ContentType,
                        ContentType.Application.Json,
                    )
                    setBody(CreateNextActionRequest("draw for 10 minutes"))
                }

            // assert
            assertEquals(Created, actual.status)
        }

    @Test
    @DataSet(value = ["datasets/setup/nextActions.yaml"], cleanBefore = true)
    @ExpectedDataSet(
        value = ["datasets/expected/nextaction/updateNextAction.yaml"],
        orderBy = ["created_at"],
    )
    fun updateNextAction() =
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
                client.put("/next-actions/e574a515-5170-4d76-afc9-da17513dc5d3") {
                    header(
                        HttpHeaders.ContentType,
                        ContentType.Application.Json,
                    )
                    setBody(UpdateNextActionRequest("practice drawing for 7 minutes"))
                }

            // assert
            assertEquals(NoContent, actual.status)
        }

    @Test
    @DataSet(value = ["datasets/setup/nextActions.yaml"], cleanBefore = true)
    @ExpectedDataSet(
        value = ["datasets/setup/nextActions.yaml"],
        orderBy = ["created_at"],
    )
    fun updateNextAction_notFound() =
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
                client.put("/next-actions/1e79998f-79ea-4fcb-95d6-e18eb33e2c8e") {
                    header(
                        HttpHeaders.ContentType,
                        ContentType.Application.Json,
                    )
                    setBody(UpdateNextActionRequest("practice drawing for 7 minutes"))
                }

            // assert
            val expected =
                Problem
                    .builder()
                    .withType(URI.create("https://example.com/problems/next-action-not-found"))
                    .withTitle("Next action not found")
                    .withStatus(Status.NOT_FOUND)
                    .withDetail("next action not found by this id : 1e79998f-79ea-4fcb-95d6-e18eb33e2c8e")
                    .build()
            assertEquals(NotFound, actual.status)
            assertEquals(expected, actual.body())
        }

    @Test
    @DataSet(value = ["datasets/setup/nextActions.yaml"], cleanBefore = true)
    @ExpectedDataSet(
        value = ["datasets/expected/nextaction/deleteNextAction.yaml"],
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
            val actual = client.delete("/next-actions/e574a515-5170-4d76-afc9-da17513dc5d3")

            // assert
            assertEquals(NoContent, actual.status)
        }

    private fun formatAsExpected(preExpected: String): String =
        preExpected
            .lineSequence()
            .map { it.trim() }
            .joinToString("")
}
