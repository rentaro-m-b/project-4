package com.example.domain.stickynote

import java.util.UUID

interface StickyNoteRepository {
    fun listStickyNotes(): List<StickyNote>

    fun createStickyNote(stickyNote: StickyNote)

    fun updateStickyNote(stickyNote: StickyNote)

    fun deleteStickyNote(id: UUID)
}