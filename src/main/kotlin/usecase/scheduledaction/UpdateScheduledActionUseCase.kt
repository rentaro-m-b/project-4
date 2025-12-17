package com.example.usecase.scheduledaction

import com.example.domain.shceduledaction.ScheduledAction
import com.example.domain.shceduledaction.ScheduledActionRepository
import java.time.LocalDateTime
import java.util.*

class UpdateScheduledActionUseCase(
    val scheduledActionRepository: ScheduledActionRepository,
) {
    fun handle(command: UpdateScheduledActionCommand) {
        val currentScheduledAction = scheduledActionRepository.fetchScheduledAction(command.id)
        if (currentScheduledAction == null) return

        val scheduledAction =
            ScheduledAction.create(
                id = command.id,
                description = command.description,
                startsAt = command.startsAt,
                endsAt = command.endsAt,
                createdAt = currentScheduledAction.createdAt,
            )
        scheduledActionRepository.updateScheduledAction(scheduledAction)
    }
}

data class UpdateScheduledActionCommand(
    val id: UUID,
    val description: String,
    val startsAt: LocalDateTime,
    val endsAt: LocalDateTime,
)
