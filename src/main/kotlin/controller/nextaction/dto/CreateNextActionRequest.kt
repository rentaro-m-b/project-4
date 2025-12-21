package com.example.controller.nextaction.dto

import com.example.usecase.nextaction.CreateNextActionCommand
import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
data class CreateNextActionRequest(
    val description: String,
) {
    fun toCommand(id: UUID): CreateNextActionCommand = CreateNextActionCommand(id, description)
}
