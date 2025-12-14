package com.example.usecase.scheduledaction

import com.example.domain.nextaction.NextAction
import com.example.domain.nextaction.NextActionRepository
import com.example.domain.stickynote.StickyNoteRepository
import java.time.LocalDateTime
import java.util.UUID

class CreateScheduledActionUseCase(
    val nextActionRepository: NextActionRepository,
    val stickyNoteRepository: StickyNoteRepository,
) {
    fun handle(command: CreateNextActionCommand) {
        if (stickyNoteRepository.fetchStickyNote(command.stickyNoteId) == null) return
        val nextAction =
            NextAction.create(
                id = UUID.randomUUID(),
                description = command.description,
                createdAt = LocalDateTime.now(),
            )
        nextActionRepository.createNextAction(nextAction)
    }
}

data class CreateNextActionCommand(
    val stickyNoteId: UUID,
    val description: String,
)
