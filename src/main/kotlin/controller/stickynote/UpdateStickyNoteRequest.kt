package com.example.controller.stickynote

import com.example.usecase.stickynote.UpdateStickyNoteCommand
import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
data class UpdateStickyNoteRequest(
    val concern: String,
) {
    fun toCommand(id: UUID): UpdateStickyNoteCommand = UpdateStickyNoteCommand(id, concern)
}
