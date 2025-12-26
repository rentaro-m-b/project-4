package com.example.usecase.scheduledaction

import com.example.domain.nextaction.NextActionRepository
import com.example.domain.shceduledaction.ScheduledActionRepository
import java.util.UUID

class DeleteScheduledActionUseCase(
    private val scheduledActionRepository: ScheduledActionRepository,
) {
    fun handle(command: DeleteScheduledActionCommand) {
        scheduledActionRepository.deleteScheduledAction(command.id)
    }
}

data class DeleteScheduledActionCommand(
    val id: UUID,
)
