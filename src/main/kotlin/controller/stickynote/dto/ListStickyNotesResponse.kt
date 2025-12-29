package com.example.controller.stickynote.dto

import com.example.domain.stickynote.StickyNote
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.time.LocalDateTime

@Serializable
data class ListStickyNotesResponse(
    val id: String,
    val concern: String,
    @Contextual
    val createdAt: LocalDateTime,
) {
    companion object {
        fun of(stickyNote: StickyNote): ListStickyNotesResponse =
            ListStickyNotesResponse(
                id = stickyNote.id.toString(),
                concern = stickyNote.concern,
                createdAt = stickyNote.createdAt,
            )
    }
}
