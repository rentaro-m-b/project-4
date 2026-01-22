package com.example.usecase.stickynote

import com.example.domain.stickynote.StickyNote
import com.example.domain.stickynote.StickyNoteRepository
import jakarta.inject.Singleton

@Singleton
class ListStickyNotesUseCase(
    private val stickyNoteRepository: StickyNoteRepository,
) {
    fun handle(): List<StickyNote> = stickyNoteRepository.listStickyNotes()
}
