package ut.controller

import com.example.configureRouting
import com.example.configureSerialization
import com.example.controller.common.ErrorResponse
import com.example.controller.stickynote.dto.CreateStickyNoteRequest
import com.example.controller.stickynote.dto.UpdateStickyNoteRequest
import com.example.module
import com.example.usecase.common.CurrentStickyNoteNotFoundException
import com.example.usecase.stickynote.CreateStickyNoteCommand
import com.example.usecase.stickynote.CreateStickyNoteUseCase
import com.example.usecase.stickynote.DeleteStickyNoteCommand
import com.example.usecase.stickynote.DeleteStickyNoteUseCase
import com.example.usecase.stickynote.ListStickyNotesUseCase
import com.example.usecase.stickynote.UpdateStickyNoteCommand
import com.example.usecase.stickynote.UpdateStickyNoteUseCase
import domain.testdatum.StickyNoteTestDatum.STICKY_NOTE_1
import domain.testdatum.StickyNoteTestDatum.STICKY_NOTE_2
import domain.testdatum.StickyNoteTestDatum.STICKY_NOTE_3
import domain.testdatum.StickyNoteTestDatum.STICKY_NOTE_4
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
import io.ktor.server.application.install
import io.ktor.server.config.ApplicationConfig
import io.ktor.server.testing.testApplication
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.verify
import org.koin.dsl.module
import org.koin.ktor.plugin.Koin
import java.util.UUID
import kotlin.test.Test
import kotlin.test.assertEquals

class StickyNoteRoutesTest {
    @Test
    fun listStickyNotes() =
        testApplication {
            // setup
            val listStickyNotesUseCase = mockk<ListStickyNotesUseCase>()
            application {
                configureSerialization()
                configureRouting()
                install(Koin) {
                    modules(
                        module {
                            single { listStickyNotesUseCase }
                        },
                    )
                }
            }

            every { listStickyNotesUseCase.handle() } returns
                listOf(
                    STICKY_NOTE_1,
                    STICKY_NOTE_2,
                    STICKY_NOTE_3,
                    STICKY_NOTE_4,
                )

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
    fun listStickyNotesNoContents() =
        testApplication {
            // setup
            val listStickyNotesUseCase = mockk<ListStickyNotesUseCase>()
            application {
                configureSerialization()
                configureRouting()
                install(Koin) {
                    modules(
                        module {
                            single { listStickyNotesUseCase }
                        },
                    )
                }
            }

            every { listStickyNotesUseCase.handle() } returns listOf()

            // execute
            val response = client.get("/sticky-notes")

            // assert
            val expected = formatAsExpected("[]")
            assertEquals(OK, response.status)
            assertEquals(expected, response.body())
        }

    @Test
    fun createStickyNote() =
        testApplication {
            // setup
            val createStickyNoteUseCase = mockk<CreateStickyNoteUseCase>()
            application {
                configureSerialization()
                configureRouting()
                install(Koin) {
                    modules(
                        module {
                            single { createStickyNoteUseCase }
                        },
                    )
                }
            }

            every {
                createStickyNoteUseCase.handle(CreateStickyNoteCommand("wanting to have happiness"))
            } returns UUID.fromString("7147553a-0338-4ee4-b9e8-ddea8b6bc311")

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
            val expected = "/sticky-notes/7147553a-0338-4ee4-b9e8-ddea8b6bc311"
            assertEquals(Created, actual.status)
            assertEquals(expected, actual.headers["Location"])
        }

    @Test
    fun updateStickyNote() =
        testApplication {
            // setup
            val updateStickyNoteUseCase = mockk<UpdateStickyNoteUseCase>()
            application {
                configureSerialization()
                configureRouting()
                install(Koin) {
                    modules(
                        module {
                            single { updateStickyNoteUseCase }
                        },
                    )
                }
            }

            every {
                updateStickyNoteUseCase.handle(
                    UpdateStickyNoteCommand(
                        id = UUID.fromString("ae95e722-253d-4fde-94f7-598da746cf0c"),
                        concern = "wanting to have happiness",
                    ),
                )
            } returns Result.success(Unit)

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
    fun updateStickyNote_notFound() =
        testApplication {
            // setup
            val updateStickyNoteUseCase = mockk<UpdateStickyNoteUseCase>()
            application {
                configureSerialization()
                configureRouting()
                install(Koin) {
                    modules(
                        module {
                            single { updateStickyNoteUseCase }
                        },
                    )
                }
            }

            every {
                updateStickyNoteUseCase.handle(
                    UpdateStickyNoteCommand(
                        id = UUID.fromString("cee8b174-fe19-47ac-b15b-d665c268c661"),
                        concern = "wanting to have happiness",
                    ),
                )
            } returns Result.failure(CurrentStickyNoteNotFoundException("sticky note not found : cee8b174-fe19-47ac-b15b-d665c268c661"))

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
        }

    @Test
    fun deleteStickyNote() =
        testApplication {
            // setup
            val deleteStickyNoteUseCase = mockk<DeleteStickyNoteUseCase>()
            application {
                configureSerialization()
                configureRouting()
                install(Koin) {
                    modules(
                        module {
                            single { deleteStickyNoteUseCase }
                        },
                    )
                }
            }

            every {
                deleteStickyNoteUseCase.handle(
                    DeleteStickyNoteCommand(UUID.fromString("ae95e722-253d-4fde-94f7-598da746cf0c")),
                )
            } just Runs

            // execute
            val actual = client.delete("/sticky-notes/ae95e722-253d-4fde-94f7-598da746cf0c")

            // assert
            verify(exactly = 1) {
                deleteStickyNoteUseCase.handle(
                    DeleteStickyNoteCommand(
                        id = UUID.fromString("ae95e722-253d-4fde-94f7-598da746cf0c"),
                    ),
                )
            }
            assertEquals(NoContent, actual.status)
        }

    private fun formatAsExpected(preExpected: String): String =
        preExpected
            .lineSequence()
            .map { it.trim() }
            .joinToString("")
}
