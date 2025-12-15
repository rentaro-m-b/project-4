package com.example.controller.scheduledaction

import com.example.usecase.nextaction.UpdateNextActionCommand
import com.example.usecase.scheduledaction.UpdateScheduledActionCommand
import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
data class UpdateScheduledActionRequest(
    val description: String,
) {
    fun toCommand(id: UUID): UpdateScheduledActionCommand = UpdateScheduledActionCommand(id, description)
}
