package com.example.controller.nextaction.dto

import com.example.usecase.nextaction.UpdateNextActionCommand
import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
data class UpdateNextActionRequest(
    val description: String,
) {
    fun toCommand(id: UUID): UpdateNextActionCommand = UpdateNextActionCommand(id, description)
}
