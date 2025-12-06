package com.example.usecase.stickynote

import com.example.domain.stickynote.StickyNote
import com.example.domain.stickynote.StickyNoteRepository
import java.util.*

class UpdateStickyNoteUseCase(
    val stickyNoteRepository: StickyNoteRepository,
) {
    fun handle(command: UpdateStickyNoteCommand) {
        val currentStickyNote = stickyNoteRepository.fetchStickyNote(command.id)
        if (currentStickyNote == null) return

        val stickyNote =
            StickyNote.create(
                id = command.id,
                concern = command.concern,
                createdAt = currentStickyNote.createdAt,
            )
        stickyNoteRepository.updateStickyNote(stickyNote)
    }
}

data class UpdateStickyNoteCommand(
    val id: UUID,
    val concern: String,
)
