package ut.usecase

import com.example.domain.stickynote.StickyNote
import com.example.domain.stickynote.StickyNoteRepository
import com.example.usecase.stickynote.ListStickyNotesUseCase
import domain.testdatum.StickyNoteTestDatum.STICKY_NOTE_1
import domain.testdatum.StickyNoteTestDatum.STICKY_NOTE_2
import domain.testdatum.StickyNoteTestDatum.STICKY_NOTE_3
import domain.testdatum.StickyNoteTestDatum.STICKY_NOTE_4
import io.mockk.every
import io.mockk.mockk
import kotlin.test.Test
import kotlin.test.assertEquals

class ListStickyNotesUseCaseTest {
    private val stickyNoteRepository = mockk<StickyNoteRepository>()
    private val target = ListStickyNotesUseCase(stickyNoteRepository)

    @Test
    fun normal() {
        // setup
        every { stickyNoteRepository.listStickyNotes() } returns
            listOf(
                STICKY_NOTE_1,
                STICKY_NOTE_2,
                STICKY_NOTE_3,
                STICKY_NOTE_4,
            )

        // execute
        val actual = target.handle()

        // assert
        val expected =
            listOf(
                STICKY_NOTE_1,
                STICKY_NOTE_2,
                STICKY_NOTE_3,
                STICKY_NOTE_4,
            )

        assertEquals(expected, actual)
    }
}
