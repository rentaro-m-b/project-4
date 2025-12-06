package com.example.controller.stickynote

import com.example.usecase.stickynote.CreateStickyNoteCommand
import com.example.usecase.stickynote.DeleteStickyNoteCommand
import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
data class DeleteStickyNoteRequest(
    val concern: String,
) {
    fun toCommand(id: UUID): DeleteStickyNoteCommand = DeleteStickyNoteCommand(id)
}
