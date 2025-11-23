package com.example.domain.stickynote

interface StickyNoteRepository {
    fun listStickyNotes(): List<StickyNote>
}