package com.example.controller.scheduledaction

import com.example.usecase.scheduledaction.CreateScheduledActionCommand
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.time.LocalDateTime
import java.util.UUID

@Serializable
data class CreateScheduledActionRequest(
    val description: String,
    @Contextual
    val startsAt: LocalDateTime,
    @Contextual
    val endsAt: LocalDateTime,
) {
    fun toCommand(id: UUID): CreateScheduledActionCommand =
        CreateScheduledActionCommand(
            stickyNoteId = id,
            description = description,
            startsAt = startsAt,
            endsAt = endsAt,
        )
}
