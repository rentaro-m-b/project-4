package com.example.controller.scheduledaction

import com.example.usecase.scheduledaction.UpdateScheduledActionCommand
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.time.LocalDateTime
import java.util.UUID

@Serializable
data class UpdateScheduledActionRequest(
    val description: String,
    @Contextual
    val startsAt: LocalDateTime,
    @Contextual
    val endsAt: LocalDateTime,
) {
    fun toCommand(id: UUID): UpdateScheduledActionCommand = UpdateScheduledActionCommand(id, description)
}
