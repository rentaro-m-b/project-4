package com.example.usecase.nextaction

import com.example.domain.nextaction.NextAction
import com.example.domain.nextaction.NextActionRepository
import com.example.domain.stickynote.StickyNoteRepository
import com.example.usecase.common.CurrentStickyNoteNotFoundException
import java.time.LocalDateTime
import java.util.UUID

class CreateNextActionUseCase(
    val nextActionRepository: NextActionRepository,
    val stickyNoteRepository: StickyNoteRepository,
) {
    fun handle(command: CreateNextActionCommand): Result<UUID> {
        if (stickyNoteRepository.fetchStickyNote(command.stickyNoteId) == null) {
            return Result.failure(CurrentStickyNoteNotFoundException("sticky note not found : ${command.stickyNoteId}"))
        }
        val nextAction =
            NextAction.create(
                id = UUID.randomUUID(),
                description = command.description,
                createdAt = LocalDateTime.now(),
            )
        nextActionRepository.createNextAction(nextAction)
        return Result.success(nextAction.id)
    }
}

data class CreateNextActionCommand(
    val stickyNoteId: UUID,
    val description: String,
)
