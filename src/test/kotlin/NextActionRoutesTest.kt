import com.example.controller.nextaction.CreateNextActionRequest
import com.example.controller.stickynote.CreateStickyNoteRequest
import com.example.controller.stickynote.DeleteStickyNoteRequest
import com.example.controller.stickynote.UpdateStickyNoteRequest
import com.example.module
import com.github.database.rider.core.api.dataset.DataSet
import com.github.database.rider.core.api.dataset.ExpectedDataSet
import com.github.database.rider.junit5.api.DBRider
import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.http.HttpStatusCode.Companion.NoContent
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.config.ApplicationConfig
import io.ktor.server.testing.*
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

@DBRider
class NextActionRoutesTest {
//    @Test
//    @DataSet(value = ["datasets/setup/nextActions.yaml"], cleanBefore = true)
//    fun listStickyNotes() = testApplication {
//        // setup
//        environment {
//            config = ApplicationConfig("application.yaml")
//        }
//        application {
//            module()
//        }
//
//        // execute
//        val response = client.get("/sticky-notes")
//
//        // assert
//        val expected =
//            formatAsExpected(
//                """
//                    [
//                        {"concern":"wanting to submit to illustration contests","createdAt":"2025-01-01T00:00:00"},
//                        {"concern":"wanting to get better at drawing","createdAt":"2025-01-01T00:00:01"},
//                        {"concern":"worrying about not losing weight","createdAt":"2025-01-01T00:00:02"},
//                        {"concern":"to read books","createdAt":"2025-01-01T00:00:03"}
//                    ]
//                """
//            )
//        assertEquals(HttpStatusCode.OK, response.status)
//        assertEquals(expected, response.body())
//    }

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
    fun createNextAction() = testApplication {
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
        val actual = client.post("/sticky-notes/ae95e722-253d-4fde-94f7-598da746cf0c/next-actions") {
            header(
                HttpHeaders.ContentType,
                ContentType.Application.Json
            )
            setBody(CreateNextActionRequest("draw for 10 minutes"))
        }

        // assert
        assertEquals(HttpStatusCode.Created, actual.status)
    }

//    @Test
//    @DataSet(value = ["datasets/setup/stickyNotes.yaml"], cleanBefore = true)
//    @ExpectedDataSet(
//        value = ["datasets/expected/updateStickyNote.yaml"],
//        orderBy = ["created_at"],
//    )
//    fun updateStickyNote() = testApplication {
//        // setup
//        environment {
//            config = ApplicationConfig("application.yaml")
//        }
//        application {
//            module()
//        }
//
//        // execute
//        val client = createClient {
//            install(ContentNegotiation) {
//                json()
//            }
//        }
//        val actual = client.put("/sticky-notes/ae95e722-253d-4fde-94f7-598da746cf0c") {
//            header(
//                HttpHeaders.ContentType,
//                ContentType.Application.Json
//            )
//            setBody(UpdateStickyNoteRequest("wanting to have happiness"))
//        }
//
//        // assert
//        assertEquals(NoContent, actual.status)
//    }
//
//    @Test
//    @DataSet(value = ["datasets/setup/stickyNotes.yaml"], cleanBefore = true)
//    @ExpectedDataSet(
//        value = ["datasets/expected/deleteStickyNote.yaml"],
//        orderBy = ["created_at"],
//    )
//    fun deleteStickyNote() = testApplication {
//        // setup
//        environment {
//            config = ApplicationConfig("application.yaml")
//        }
//        application {
//            module()
//        }
//
//        // execute
//        val client = createClient {
//            install(ContentNegotiation) {
//                json()
//            }
//        }
//        val actual = client.delete("/sticky-notes/ae95e722-253d-4fde-94f7-598da746cf0c") {
//            header(
//                HttpHeaders.ContentType,
//                ContentType.Application.Json
//            )
//            setBody(DeleteStickyNoteRequest("wanting to have happiness"))
//        }
//
//        // assert
//        assertEquals(NoContent, actual.status)
//    }

    private fun formatAsExpected(preExpected: String): String =
        preExpected
            .lineSequence().map { it.trim() }
            .joinToString("")
}
