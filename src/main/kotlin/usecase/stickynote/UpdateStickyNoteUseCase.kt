package com.example.usecase.stickynote

import com.example.domain.stickynote.StickyNote
import com.example.domain.stickynote.StickyNoteRepository
import com.example.usecase.common.CurrentStickyNoteNotFoundException
import java.util.UUID

class UpdateStickyNoteUseCase(
    val stickyNoteRepository: StickyNoteRepository,
) {
    fun handle(command: UpdateStickyNoteCommand): Result<Unit> {
        val currentStickyNote = stickyNoteRepository.fetchStickyNote(command.id)
        if (currentStickyNote == null) {
            return Result.failure(CurrentStickyNoteNotFoundException("sticky note not found : ${command.id}"))
        }

        val stickyNote =
            StickyNote.create(
                id = command.id,
                concern = command.concern,
                createdAt = currentStickyNote.createdAt,
            )
        stickyNoteRepository.updateStickyNote(stickyNote)

        return Result.success(Unit)
    }
}

data class UpdateStickyNoteCommand(
    val id: UUID,
    val concern: String,
)
