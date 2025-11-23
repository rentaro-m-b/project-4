import com.example.module
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
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

    private fun formatAsExpected(preExpected: String): String =
        preExpected
            .lineSequence().map { it.trim() }
            .joinToString("")
}