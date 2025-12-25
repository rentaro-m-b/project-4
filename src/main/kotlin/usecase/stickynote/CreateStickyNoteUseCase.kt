package com.example.usecase.stickynote

import com.example.domain.stickynote.StickyNote
import com.example.domain.stickynote.StickyNoteRepository
import java.time.LocalDateTime
import java.util.UUID

class CreateStickyNoteUseCase(
    val stickyNoteRepository: StickyNoteRepository,
) {
    fun handle(command: CreateStickyNoteCommand): UUID {
        val stickyNote =
            StickyNote.create(
                id = UUID.randomUUID(),
                concern = command.concern,
                createdAt = LocalDateTime.now(),
            )
        stickyNoteRepository.createStickyNote(stickyNote)
        return stickyNote.id
    }
}

data class CreateStickyNoteCommand(
    val concern: String,
)
