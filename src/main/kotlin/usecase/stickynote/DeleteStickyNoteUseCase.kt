package com.example.usecase.stickynote

import com.example.domain.stickynote.StickyNoteRepository
import java.util.UUID

class DeleteStickyNoteUseCase(
    private val stickyNoteRepository: StickyNoteRepository,
) {
    fun handle(command: DeleteStickyNoteCommand) {
        stickyNoteRepository.deleteStickyNote(command.id)
    }
}

data class DeleteStickyNoteCommand(
    val id: UUID,
)
