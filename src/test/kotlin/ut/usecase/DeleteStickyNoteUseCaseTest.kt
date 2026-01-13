package ut.usecase

import com.example.domain.stickynote.StickyNoteRepository
import com.example.usecase.stickynote.DeleteStickyNoteCommand
import com.example.usecase.stickynote.DeleteStickyNoteUseCase
import domain.testdatum.StickyNoteTestDatum.STICKY_NOTE_1
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.verify
import java.util.UUID
import kotlin.test.Test

class DeleteStickyNoteUseCaseTest {
    private val stickyNoteRepository = mockk<StickyNoteRepository>()
    private val target = DeleteStickyNoteUseCase(stickyNoteRepository)

    @Test
    fun normal() {
        // setup
        every {
            stickyNoteRepository.deleteStickyNote(STICKY_NOTE_1.id)
        } just Runs

        // execute
        target.handle(DeleteStickyNoteCommand(UUID.fromString("ae95e722-253d-4fde-94f7-598da746cf0c")))

        // assert
        verify(exactly = 1) {
            target.handle(DeleteStickyNoteCommand(UUID.fromString("ae95e722-253d-4fde-94f7-598da746cf0c")))
        }
    }
}
