package com.example.usecase.stickynote

import com.example.domain.stickynote.StickyNote
import com.example.domain.stickynote.StickyNoteRepository

class CreateStickyNoteUseCase(
    val stickyNoteRepository: StickyNoteRepository,
) {
    fun handle(stickyNote: StickyNote) {
        stickyNoteRepository.createStickyNote(stickyNote)
    }
}