package ut.usecase

import com.example.domain.stickynote.StickyNote
import com.example.domain.stickynote.StickyNoteRepository
import com.example.usecase.stickynote.CreateStickyNoteCommand
import com.example.usecase.stickynote.CreateStickyNoteUseCase
import com.example.usecase.stickynote.IdGenerator
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import java.time.LocalDateTime
import java.util.UUID
import kotlin.test.Test
import kotlin.test.assertEquals

class CreateStickyNoteUseCaseTest {
    private val idGenerator = mockk<IdGenerator>()
    private val stickyNoteRepository = mockk<StickyNoteRepository>()
    private val target = CreateStickyNoteUseCase(stickyNoteRepository, idGenerator)

    @Test
    fun normal() {
        // setup
        every { idGenerator.generateId() } returns UUID.fromString("7147553a-0338-4ee4-b9e8-ddea8b6bc311")
        every {
            stickyNoteRepository.createStickyNote(
                StickyNote.create(
                    id = UUID.fromString("7147553a-0338-4ee4-b9e8-ddea8b6bc311"),
                    concern = "wanting to have happiness",
                    createdAt = LocalDateTime.parse("2025-02-01T00:00:00"),
                ),
            )
        } just Runs

        // execute
        val actual = target.handle(CreateStickyNoteCommand("wanting to have happiness"))

        // assert
        val expected = UUID.fromString("7147553a-0338-4ee4-b9e8-ddea8b6bc311")

        assertEquals(expected, actual)
    }
}
