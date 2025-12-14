package com.example.controller.scheduledaction

import com.example.usecase.nextaction.CreateNextActionCommand
import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
data class CreateScheduledActionRequest(
    val description: String,
) {
    fun toCommand(id: UUID): CreateScheduledActionCommand = CreateScheduledActionCommand(id, description)
}
