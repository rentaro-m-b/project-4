import com.example.controller.stickynote.CreateStickyNoteRequest
import com.example.module
import com.github.database.rider.core.api.dataset.DataSet
import com.github.database.rider.core.api.dataset.ExpectedDataSet
import com.github.database.rider.junit5.api.DBRider
import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.config.ApplicationConfig
import io.ktor.server.testing.*
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

@DBRider
class StickyNoteRoutesTest {
    @Test
    @DataSet(value = ["datasets/setup/stickyNotes.yaml"], cleanBefore = true)
    fun listStickyNotes() = testApplication {
        // setup
        environment {
            config = ApplicationConfig("application.yaml")
        }
        application {
            module()
        }

        // execute
        val response = client.get("/stickyNotes")

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
                """
            )
        assertEquals(HttpStatusCode.OK, response.status)
        assertEquals(expected, response.body())
    }

    @Test
    @DataSet(value = ["datasets/setup/stickyNotes.yaml"], cleanBefore = true)
    @ExpectedDataSet(
        value = ["datasets/expected/createStickyNote.yaml"],
        orderBy = ["created_at"],
        ignoreCols = ["created_at"],
    )
    fun createStickyNote() = testApplication {
        // setup
        environment {
            config = ApplicationConfig("application.yaml")
        }
        application {
            module()
        }

        // execute
        val client = createClient {
            install(ContentNegotiation) {
                json()
            }
        }
        val responseCreated = client.post("/stickyNotes") {
            header(
                HttpHeaders.ContentType,
                ContentType.Application.Json
            )
            setBody(CreateStickyNoteRequest("wanting to have happiness"))
        }

        // assert
        assertEquals(HttpStatusCode.Created, responseCreated.status)
    }

    private fun formatAsExpected(preExpected: String): String =
        preExpected
            .lineSequence().map { it.trim() }
            .joinToString("")
}