package com.example.controller.stickynote

import com.example.usecase.stickynote.CreateStickyNoteCommand
import kotlinx.serialization.Serializable

@Serializable
data class CreateStickyNoteRequest(
    val concern: String,
) {
    fun toCommand(): CreateStickyNoteCommand = CreateStickyNoteCommand(concern)
}
