package com.example.usecase.stickynote

import com.example.domain.stickynote.StickyNoteRepository
import jakarta.inject.Singleton
import java.util.UUID

@Singleton
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
