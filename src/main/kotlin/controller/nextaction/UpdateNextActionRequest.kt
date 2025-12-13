package com.example.controller.nextaction

import com.example.usecase.nextaction.UpdateNextActionCommand
import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
data class UpdateNextActionRequest(
    val description: String,
) {
    fun toCommand(id: UUID): UpdateNextActionCommand = UpdateNextActionCommand(id, description)
}
