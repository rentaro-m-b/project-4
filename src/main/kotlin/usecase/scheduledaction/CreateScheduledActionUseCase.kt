package com.example.usecase.scheduledaction

import com.example.domain.shceduledaction.ScheduledAction
import com.example.domain.shceduledaction.ScheduledActionRepository
import com.example.domain.stickynote.StickyNoteRepository
import java.time.LocalDateTime
import java.util.UUID

class CreateScheduledActionUseCase(
    val scheduledActionRepository: ScheduledActionRepository,
    val stickyNoteRepository: StickyNoteRepository,
) {
    fun handle(command: CreateScheduledActionCommand) {
        if (stickyNoteRepository.fetchStickyNote(command.stickyNoteId) == null) return
        val scheduledAction =
            ScheduledAction.create(
                id = UUID.randomUUID(),
                description = command.description,
                startsAt = command.startsAt,
                endsAt = command.endsAt,
                createdAt = LocalDateTime.now(),
            )
        scheduledActionRepository.createScheduledAction(scheduledAction)
    }
}

data class CreateScheduledActionCommand(
    val stickyNoteId: UUID,
    val startsAt: LocalDateTime,
    val endsAt: LocalDateTime,
    val description: String,
)
