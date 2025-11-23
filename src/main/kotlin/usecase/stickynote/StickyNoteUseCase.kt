package com.example.usecase.stickynote

import com.example.domain.stickynote.StickyNote
import com.example.domain.stickynote.StickyNoteRepository

class StickyNoteUseCase(
    val stickyNoteRepository: StickyNoteRepository,
) {
    fun handle(): List<StickyNote> {
        return stickyNoteRepository.listStickyNotes()
    }
}