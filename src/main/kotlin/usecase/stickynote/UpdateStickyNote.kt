package com.example.usecase.stickynote

import com.example.domain.stickynote.StickyNote
import com.example.domain.stickynote.StickyNoteRepository
import java.time.LocalDateTime
import java.util.UUID

class UpdateStickyNote(
    val stickyNoteRepository: StickyNoteRepository,
) {
    fun handle(command: UpdateStickyNoteCommand) {
        // TODO: find系を用意して既存のスティッキーノートを取得する
        val stickyNote =
            StickyNote.create(
                id = UUID.randomUUID(),
                concern = command.concern,
                createdAt = LocalDateTime.now(),
            )
        stickyNoteRepository.updateStickyNote(stickyNote)
    }
}

data class UpdateStickyNoteCommand(
    val id: UUID,
    val concern: String,
)
