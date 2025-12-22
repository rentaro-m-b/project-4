package com.example.usecase.scheduledaction

import com.example.domain.shceduledaction.ScheduledAction
import com.example.domain.shceduledaction.ScheduledActionRepository
import com.example.domain.stickynote.StickyNoteRepository
import com.example.usecase.common.CurrentStickyNoteNotFoundException
import java.time.LocalDateTime
import java.util.UUID

class CreateScheduledActionUseCase(
    val scheduledActionRepository: ScheduledActionRepository,
    val stickyNoteRepository: StickyNoteRepository,
) {
    fun handle(command: CreateScheduledActionCommand): Result<Unit> {
        if (stickyNoteRepository.fetchStickyNote(command.stickyNoteId) == null) {
            return Result.failure(CurrentStickyNoteNotFoundException("sticky note not found : ${command.stickyNoteId}"))
        }
        val scheduledAction =
            ScheduledAction.create(
                id = UUID.randomUUID(),
                description = command.description,
                startsAt = command.startsAt,
                endsAt = command.endsAt,
                createdAt = LocalDateTime.now(),
            )
        scheduledActionRepository.createScheduledAction(scheduledAction)

        return Result.success(Unit)
    }
}

data class CreateScheduledActionCommand(
    val stickyNoteId: UUID,
    val startsAt: LocalDateTime,
    val endsAt: LocalDateTime,
    val description: String,
)
