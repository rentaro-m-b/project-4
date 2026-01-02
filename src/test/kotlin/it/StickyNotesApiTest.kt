package it

import ch.qos.logback.classic.Level
import ch.qos.logback.classic.Logger
import ch.qos.logback.classic.spi.ILoggingEvent
import ch.qos.logback.core.read.ListAppender
import com.example.controller.common.ErrorResponse
import com.example.controller.stickynote.dto.CreateStickyNoteRequest
import com.example.controller.stickynote.dto.UpdateStickyNoteRequest
import com.example.module
import com.example.usecase.stickynote.UpdateStickyNoteUseCase
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
import org.slf4j.LoggerFactory
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@DBRider
class StickyNotesApiTest {
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
                        {"id":"ae95e722-253d-4fde-94f7-598da746cf0c","concern":"wanting to submit to illustration contests","createdAt":"2025-01-01T00:00:00"},
                        {"id":"36baaf2f-e621-4db4-b26a-9dda2db5cb29","concern":"wanting to get better at drawing","createdAt":"2025-01-01T00:00:01"},
                        {"id":"3a7c31c1-765b-4486-a05f-eefbee300be4","concern":"worrying about not losing weight","createdAt":"2025-01-01T00:00:02"},
                        {"id":"8df1df03-5e9d-4a2a-aec3-96060d27727d","concern":"to read books","createdAt":"2025-01-01T00:00:03"}
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
            assertTrue(actual.headers["Location"]!!.startsWith("/sticky-notes/"))
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
        value = ["datasets/setup/stickyNotes.yaml"],
        orderBy = ["created_at"],
    )
    fun updateStickyNote_notFound() =
        testApplication {
            // setup
            environment {
                config = ApplicationConfig("application.yaml")
            }
            application {
                module()
            }

            val appender = setUpAppender(UpdateStickyNoteUseCase::class.java)

            // execute
            val client =
                createClient {
                    install(ContentNegotiation) {
                        json()
                    }
                }
            val actual =
                client.put("/sticky-notes/cee8b174-fe19-47ac-b15b-d665c268c661") {
                    header(
                        HttpHeaders.ContentType,
                        ContentType.Application.Json,
                    )
                    setBody(UpdateStickyNoteRequest("wanting to have happiness"))
                }

            // assert
            val expected =
                ErrorResponse(
                    type = "blanck",
                    title = "Not found sticky note.",
                    detail = "No sticky note matching the id was found.",
                    instance = "/sticky-notes/cee8b174-fe19-47ac-b15b-d665c268c661",
                )
            assertEquals(NotFound, actual.status)
            assertEquals(expected, actual.body())
            val events = appender.list
            assert(events.size == 1)
            assert(events[0].level == Level.WARN)
            assert(events[0].formattedMessage == "sticky note not found : cee8b174-fe19-47ac-b15b-d665c268c661")
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

    private fun <T> setUpAppender(logId: Class<T>): ListAppender<ILoggingEvent> {
        val log = LoggerFactory.getLogger(logId) as Logger
        val appender = ListAppender<ILoggingEvent>()
        appender.start()
        log.addAppender(appender)
        return appender
    }
}
