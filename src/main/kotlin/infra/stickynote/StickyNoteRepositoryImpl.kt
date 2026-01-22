package com.example.infra.stickynote

import com.example.domain.stickynote.StickyNote
import com.example.domain.stickynote.StickyNoteRepository
import com.example.tables.StickyNotes.STICKY_NOTES
import com.example.tables.records.StickyNotesRecord
import jakarta.inject.Singleton
import org.jooq.DSLContext
import java.util.UUID

@Singleton
class StickyNoteRepositoryImpl(
    val dslContext: DSLContext,
) : StickyNoteRepository {
    override fun listStickyNotes(): List<StickyNote> {
        val records = dslContext.selectFrom(STICKY_NOTES).fetch().toList()
        return records.map { it.toEntity() }
    }

    override fun fetchStickyNote(id: UUID): StickyNote? {
        val record = dslContext.selectFrom(STICKY_NOTES).where(STICKY_NOTES.ID.eq(id)).fetchOne()
        return record?.toEntity()
    }

    override fun createStickyNote(stickyNote: StickyNote) {
        val record =
            dslContext.newRecord(STICKY_NOTES).apply {
                id = UUID.randomUUID()
                concern = stickyNote.concern
                createdAt = stickyNote.createdAt
            }
        record.store()
    }

    override fun updateStickyNote(stickyNote: StickyNote) {
        val record = dslContext.selectFrom(STICKY_NOTES).where(STICKY_NOTES.ID.eq(stickyNote.id)).fetchOne()
        // TODO: early return
        if (record != null) {
            record.concern = stickyNote.concern
            record.store()
        }
    }

    override fun deleteStickyNote(id: UUID) {
        val record = dslContext.selectFrom(STICKY_NOTES).where(STICKY_NOTES.ID.eq(id)).fetchOne()
        record?.delete()
    }
}

fun StickyNotesRecord.toEntity(): StickyNote =
    StickyNote.create(
        id = id,
        concern = concern,
        imageKey = s3Key,
        createdAt = createdAt,
    )
