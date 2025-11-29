package com.example.infra.stickynote

import com.example.domain.stickynote.StickyNote
import com.example.domain.stickynote.StickyNoteRepository
import org.jooq.*
import org.jooq.impl.DSL
import org.jooq.tools.jdbc.JDBCUtils.dialect
import java.time.LocalDateTime
import java.util.*


object StickyNoteRepositoryImpl: StickyNoteRepository {
    private val stickyNotes =
        mutableListOf(
            StickyNote(concern = "wanting to submit to illustration contests", LocalDateTime.parse("2025-01-01T00:00:00")),
            StickyNote(concern = "wanting to get better at drawing", LocalDateTime.parse("2025-01-01T00:00:00")),
            StickyNote(concern = "worrying about not losing weight", LocalDateTime.parse("2025-01-01T00:00:00")),
            StickyNote(concern = "to read books", LocalDateTime.parse("2025-01-01T00:00:00")),
        )

    val dsl = DSL.using(connection, dialect)

    override fun listStickyNotes(): List<StickyNote> {


        return stickyNotes
    }

    override fun createStickyNote(stickyNote: StickyNote) {
        stickyNotes.add(stickyNote)
    }
}