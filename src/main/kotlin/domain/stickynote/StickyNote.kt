package com.example.domain.stickynote

import java.time.LocalDateTime
import java.util.UUID

class StickyNote private constructor(
    val id: UUID,
    val concern: String,
    val s3Key: String,
    val createdAt: LocalDateTime,
) {
    companion object {
        fun create(
            id: UUID,
            concern: String,
            imageKey: String,
            createdAt: LocalDateTime,
        ): StickyNote =
            StickyNote(
                id = id,
                concern = concern,
                s3Key = imageKey,
                createdAt = createdAt,
            )
    }

    override operator fun equals(other: Any?): Boolean = other is StickyNote && other.id == id

    override fun hashCode(): Int = id.hashCode()
}
