import com.example.controller.stickynote.CreateStickyNoteRequest
import com.example.controller.stickynote.UpdateStickyNoteRequest
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
class StickyNoteRoutesTest {
    @Test
    @DataSet(value = ["datasets/setup/stickyNotes.yaml"], cleanBefore = true)
    fun listStickyNotes() =
        testApplication {
            // setup
            environment {
                config = ApplicationConfig("application.yaml")
            }
            application {
                module()
            }

            // execute
            val response = client.get("/sticky-notes")

            // assert
            val expected =
                formatAsExpected(
                    """
                    [
                        {"concern":"wanting to submit to illustration contests","createdAt":"2025-01-01T00:00:00"},
                        {"concern":"wanting to get better at drawing","createdAt":"2025-01-01T00:00:01"},
                        {"concern":"worrying about not losing weight","createdAt":"2025-01-01T00:00:02"},
                        {"concern":"to read books","createdAt":"2025-01-01T00:00:03"}
                    ]
                """,
                )
            assertEquals(OK, response.status)
            assertEquals(expected, response.body())
        }

    @Test
    @DataSet(value = ["datasets/setup/stickyNotes.yaml"], cleanBefore = true)
    @ExpectedDataSet(
        value = ["datasets/expected/stickynote/createStickyNote.yaml"],
        orderBy = ["created_at"],
    )
    fun createStickyNote() =
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
                client.post("/sticky-notes") {
                    header(
                        HttpHeaders.ContentType,
                        ContentType.Application.Json,
                    )
                    setBody(CreateStickyNoteRequest("wanting to have happiness"))
                }

            // assert
            assertEquals(Created, actual.status)
        }

    @Test
    @DataSet(value = ["datasets/setup/stickyNotes.yaml"], cleanBefore = true)
    @ExpectedDataSet(
        value = ["datasets/expected/stickynote/updateStickyNote.yaml"],
        orderBy = ["created_at"],
    )
    fun updateStickyNote() =
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
                client.put("/sticky-notes/ae95e722-253d-4fde-94f7-598da746cf0c") {
                    header(
                        HttpHeaders.ContentType,
                        ContentType.Application.Json,
                    )
                    setBody(UpdateStickyNoteRequest("wanting to have happiness"))
                }

            // assert
            assertEquals(NoContent, actual.status)
        }

    @Test
    @DataSet(value = ["datasets/setup/stickyNotes.yaml"], cleanBefore = true)
    @ExpectedDataSet(
        value = ["datasets/expected/stickynote/deleteStickyNote.yaml"],
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
            val actual = client.delete("/sticky-notes/ae95e722-253d-4fde-94f7-598da746cf0c")

            // assert
            assertEquals(NoContent, actual.status)
        }

    private fun formatAsExpected(preExpected: String): String =
        preExpected
            .lineSequence()
            .map { it.trim() }
            .joinToString("")
}
