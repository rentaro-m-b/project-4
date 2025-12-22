package com.example.usecase.scheduledaction

import com.example.domain.shceduledaction.ScheduledAction
import com.example.domain.shceduledaction.ScheduledActionRepository
import com.example.usecase.common.usecase.common.CurrentScheduledActionNotFoundException
import java.time.LocalDateTime
import java.util.UUID

class UpdateScheduledActionUseCase(
    val scheduledActionRepository: ScheduledActionRepository,
) {
    fun handle(command: UpdateScheduledActionCommand): Result<Unit> {
        val currentScheduledAction = scheduledActionRepository.fetchScheduledAction(command.id)
        if (currentScheduledAction == null) {
            return Result.failure(CurrentScheduledActionNotFoundException("scheduled action not found : ${command.id}"))
        }

        val scheduledAction =
            ScheduledAction.create(
                id = command.id,
                description = command.description,
                startsAt = command.startsAt,
                endsAt = command.endsAt,
                createdAt = currentScheduledAction.createdAt,
            )
        scheduledActionRepository.updateScheduledAction(scheduledAction)

        return Result.success(Unit)
    }
}

data class UpdateScheduledActionCommand(
    val id: UUID,
    val description: String,
    val startsAt: LocalDateTime,
    val endsAt: LocalDateTime,
)
