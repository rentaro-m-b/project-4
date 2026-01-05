package ut.controller

import com.example.configureRouting
import com.example.configureSerialization
import com.example.domain.stickynote.StickyNote
import com.example.usecase.stickynote.ListStickyNotesUseCase
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.HttpStatusCode.Companion.OK
import io.ktor.server.application.install
import io.ktor.server.testing.testApplication
import io.mockk.every
import io.mockk.mockk
import org.koin.dsl.module
import org.koin.ktor.plugin.Koin
import java.time.LocalDateTime
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
                    StickyNote.create(
                        id = UUID.fromString("ae95e722-253d-4fde-94f7-598da746cf0c"),
                        concern = "wanting to submit to illustration contests",
                        createdAt = LocalDateTime.parse("2025-01-01T00:00:00"),
                    ),
                    StickyNote.create(
                        id = UUID.fromString("36baaf2f-e621-4db4-b26a-9dda2db5cb29"),
                        concern = "wanting to get better at drawing",
                        createdAt = LocalDateTime.parse("2025-01-01T00:00:01"),
                    ),
                    StickyNote.create(
                        id = UUID.fromString("3a7c31c1-765b-4486-a05f-eefbee300be4"),
                        concern = "worrying about not losing weight",
                        createdAt = LocalDateTime.parse("2025-01-01T00:00:02"),
                    ),
                    StickyNote.create(
                        id = UUID.fromString("8df1df03-5e9d-4a2a-aec3-96060d27727d"),
                        concern = "to read books",
                        createdAt = LocalDateTime.parse("2025-01-01T00:00:03"),
                    ),
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

    private fun formatAsExpected(preExpected: String): String =
        preExpected
            .lineSequence()
            .map { it.trim() }
            .joinToString("")
}
