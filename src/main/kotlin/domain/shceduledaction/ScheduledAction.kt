package com.example.domain.shceduledaction

import java.time.LocalDateTime
import java.util.UUID

class ScheduledAction private constructor(
    val id: UUID,
    val description: String,
    val startsAt: LocalDateTime,
    val endsAt: LocalDateTime,
    val createdAt: LocalDateTime,
) {
    companion object {
        fun create(
            id: UUID,
            description: String,
            startsAt: LocalDateTime,
            endsAt: LocalDateTime,
            createdAt: LocalDateTime,
        ): ScheduledAction =
            ScheduledAction(
                id = id,
                description = description,
                startsAt = startsAt,
                endsAt = endsAt,
                createdAt = createdAt,
            )
    }

    override operator fun equals(other: Any?): Boolean = other is ScheduledAction && other.id == id

    override fun hashCode(): Int = id.hashCode()
}
