package com.example.infra.stickynote

import com.example.DataSource
import com.example.domain.stickynote.StickyNote
import com.example.domain.stickynote.StickyNoteRepository
import com.example.tables.StickyNotes.STICKY_NOTES
import com.example.tables.records.StickyNotesRecord
import org.jooq.SQLDialect
import org.jooq.impl.DSL
import java.time.LocalDateTime

class StickyNoteRepositoryImpl(val dataSource: DataSource): StickyNoteRepository {
    private val stickyNotes =
        mutableListOf(
            StickyNote(concern = "wanting to submit to illustration contests", LocalDateTime.parse("2025-01-01T00:00:00")),
            StickyNote(concern = "wanting to get better at drawing", LocalDateTime.parse("2025-01-01T00:00:00")),
            StickyNote(concern = "worrying about not losing weight", LocalDateTime.parse("2025-01-01T00:00:00")),
            StickyNote(concern = "to read books", LocalDateTime.parse("2025-01-01T00:00:00")),
        )

    override fun listStickyNotes(): List<StickyNote> {
        val dsl = DSL.using(dataSource.getConnection(), SQLDialect.POSTGRES)
        val result = dsl.selectFrom(STICKY_NOTES).fetch().toList()
        return result.map { it.toEntity() }
    }

    override fun createStickyNote(stickyNote: StickyNote) {
        stickyNotes.add(stickyNote)
    }
}

fun StickyNotesRecord.toEntity(): StickyNote {
    return StickyNote(
        concern = concern,
        createdAt = createdAt,
    )
}
