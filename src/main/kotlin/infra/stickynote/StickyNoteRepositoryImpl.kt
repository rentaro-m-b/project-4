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
        val records = dsl.selectFrom(STICKY_NOTES).fetch().toList()
        return records.map { it.toEntity() }
    }

    override fun fetchStickyNote(id: UUID): StickyNote? {
        val record = dsl.selectFrom(STICKY_NOTES).where(STICKY_NOTES.ID.eq(id)).fetchOne()
        return record?.toEntity()
    }

    override fun createStickyNote(stickyNote: StickyNote) {
        val record = dsl.newRecord(STICKY_NOTES).apply {
            id = UUID.randomUUID()
            concern = stickyNote.concern
            createdAt = stickyNote.createdAt
        }
        record.store()
    }

    override fun updateStickyNote(stickyNote: StickyNote) {
        val record = dsl.selectFrom(STICKY_NOTES).where(STICKY_NOTES.ID.eq(stickyNote.id)).fetchOne()
        // TODO: early return
        if (record != null) {
            record.concern = stickyNote.concern
            record.store()
        }
    }

    override fun deleteStickyNote(id: UUID) {
        TODO()
    }
}

fun StickyNotesRecord.toEntity(): StickyNote {
    return StickyNote.create(
        id = id,
        concern = concern,
        createdAt = createdAt,
    )
}
