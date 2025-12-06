package com.example.controller.stickynote.controller.stickynote

import com.example.usecase.stickynote.CreateStickyNoteCommand
import com.example.usecase.stickynote.UpdateStickyNoteCommand
import com.example.usecase.stickynote.UpdateStickyNoteUseCase
import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
data class UpdateStickyNoteRequest(
    val concern: String,
) {
    fun toCommand(id: UUID): UpdateStickyNoteCommand = UpdateStickyNoteCommand(id, concern)
}
