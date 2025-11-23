package com.example.infra.stickynote

import com.example.domain.stickynote.StickyNote
import com.example.domain.stickynote.StickyNoteRepository
import kotlinx.datetime.LocalDateTime

class StickyNoteRepositoryImpl: StickyNoteRepository {
    override fun listStickyNotes(): List<StickyNote> {
        val stickyNotes = listOf(
            StickyNote(concern = "wanting to submit to illustration contests", LocalDateTime.parse("2025-01-01T00:00:00")),
            StickyNote(concern = "wanting to get better at drawing", LocalDateTime.parse("2025-01-01T00:00:00")),
            StickyNote(concern = "worrying about not losing weight", LocalDateTime.parse("2025-01-01T00:00:00")),
            StickyNote(concern = "to read books", LocalDateTime.parse("2025-01-01T00:00:00")),
        )

        return stickyNotes
    }
}