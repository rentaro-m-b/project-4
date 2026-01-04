package ut.usecase

import com.example.domain.stickynote.StickyNote
import com.example.domain.stickynote.StickyNoteRepository
import com.example.usecase.stickynote.ListStickyNotesUseCase
import io.mockk.every
import io.mockk.mockk
import java.time.LocalDateTime
import java.util.UUID
import kotlin.test.Test
import kotlin.test.assertEquals

class ListStickyNotesUseCaseTest {
    val stickyNoteRepository = mockk<StickyNoteRepository>()
    private val target = ListStickyNotesUseCase(stickyNoteRepository)

    @Test
    fun normal() {
        // setup
        every { stickyNoteRepository.listStickyNotes() } returns
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
        val actual = target.handle()

        // assert
        val expected =
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

        assertEquals(expected, actual)
    }
}
