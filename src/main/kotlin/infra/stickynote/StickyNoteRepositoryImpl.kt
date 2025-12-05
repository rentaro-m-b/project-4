package com.example.infra.stickynote

import com.example.DataSource
import com.example.domain.stickynote.StickyNote
import com.example.domain.stickynote.StickyNoteRepository
import com.example.tables.StickyNotes.STICKY_NOTES
import com.example.tables.records.StickyNotesRecord
import org.jooq.SQLDialect
import org.jooq.impl.DSL
import java.util.*

class StickyNoteRepositoryImpl(val dataSource: DataSource): StickyNoteRepository {
    private val dsl = DSL.using(dataSource.getConnection(), SQLDialect.POSTGRES)
    override fun listStickyNotes(): List<StickyNote> {
        val result = dsl.selectFrom(STICKY_NOTES).fetch().toList()
        return result.map { it.toEntity() }
    }

    override fun createStickyNote(stickyNote: StickyNote) {
        val record = dsl.newRecord(STICKY_NOTES).apply {
            id = UUID.randomUUID()
            concern = stickyNote.concern
            createdAt = stickyNote.createdAt
        }
        record.store()
    }
}

fun StickyNotesRecord.toEntity(): StickyNote {
    return StickyNote.create(
        id = id,
        concern = concern,
        createdAt = createdAt,
    )
}
