import com.example.controller.stickynote.CreateStickyNoteRequest
import com.example.module
import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.testing.*
import kotlin.test.Test
import kotlin.test.assertEquals

class StickyNoteRoutesTest {
    @Test
    fun listStickyNotes() = testApplication {
        // setup
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
                        {"concern":"wanting to submit to illustration contests","createdAt":"2025-01-01T00:00"},
                        {"concern":"wanting to get better at drawing","createdAt":"2025-01-01T00:00"},
                        {"concern":"worrying about not losing weight","createdAt":"2025-01-01T00:00"},
                        {"concern":"to read books","createdAt":"2025-01-01T00:00"}
                    ]
                """
            )
        assertEquals(HttpStatusCode.OK, response.status)
        assertEquals(expected, response.body())
    }

    @Test
    fun createStickyNote() = testApplication {
        // setup
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