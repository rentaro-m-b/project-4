package ut.usecase

import com.example.domain.stickynote.StickyNote
import com.example.domain.stickynote.StickyNoteRepository
import com.example.usecase.common.CurrentStickyNoteNotFoundException
import com.example.usecase.stickynote.UpdateStickyNoteCommand
import com.example.usecase.stickynote.UpdateStickyNoteUseCase
import domain.testdatum.StickyNoteTestDatum.STICKY_NOTE_1
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import java.util.UUID
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class UpdateStickyNoteUseCaseTest {
    private val stickyNoteRepository = mockk<StickyNoteRepository>()
    private val target = UpdateStickyNoteUseCase(stickyNoteRepository)

    @Test
    fun normal() {
        // setup
        every {
            stickyNoteRepository.fetchStickyNote(UUID.fromString("ae95e722-253d-4fde-94f7-598da746cf0c"))
        } returns STICKY_NOTE_1
        every {
            stickyNoteRepository.updateStickyNote(
                StickyNote.create(
                    id = STICKY_NOTE_1.id,
                    concern = "wanting to have happiness",
                    imageKey = "",
                    createdAt = STICKY_NOTE_1.createdAt,
                ),
            )
        } just Runs

        // execute
        val actual =
            target.handle(
                UpdateStickyNoteCommand(
                    id = UUID.fromString("ae95e722-253d-4fde-94f7-598da746cf0c"),
                    concern = "wanting to have happiness",
                ),
            )

        // assert
        val expected = Result.success(Unit)
        assertEquals(expected, actual)
    }

    @Test
    fun notFound() {
        // setup
        every {
            stickyNoteRepository.fetchStickyNote(UUID.fromString("cee8b174-fe19-47ac-b15b-d665c268c661"))
        } returns null

        // execute
        val actual =
            target.handle(
                UpdateStickyNoteCommand(
                    id = UUID.fromString("cee8b174-fe19-47ac-b15b-d665c268c661"),
                    concern = "wanting to have happiness",
                ),
            )

        // assert
        val expected = "sticky note not found : cee8b174-fe19-47ac-b15b-d665c268c661"
        assertTrue(actual.isFailure)
        assertTrue(actual.exceptionOrNull() is CurrentStickyNoteNotFoundException)
        assertEquals(expected, actual.exceptionOrNull()!!.message)
    }
}
