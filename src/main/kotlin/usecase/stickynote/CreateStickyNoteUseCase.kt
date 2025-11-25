package com.example.usecase.stickynote

import com.example.domain.stickynote.StickyNote
import com.example.domain.stickynote.StickyNoteRepository
import java.time.LocalDateTime

class CreateStickyNoteUseCase(
    val stickyNoteRepository: StickyNoteRepository,
) {
    fun handle(command: CreateStickyNoteCommand) {
        val stickyNote = StickyNote(command.concern, LocalDateTime.now())
        stickyNoteRepository.createStickyNote(stickyNote)
    }
}

data class CreateStickyNoteCommand(
    val concern: String,
)
