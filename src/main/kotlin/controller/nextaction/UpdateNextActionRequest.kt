package com.example.controller.nextaction

import com.example.usecase.nextaction.UpdateNextActionCommand
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class UpdateNextActionRequest(
    val description: String,
) {
    fun toCommand(id: UUID): UpdateNextActionCommand = UpdateNextActionCommand(id, description)
}