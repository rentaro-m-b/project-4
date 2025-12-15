package com.example.usecase.scheduledaction

import com.example.domain.nextaction.NextActionRepository
import java.util.UUID

class DeleteScheduledActionUseCase(
    val nextActionRepository: NextActionRepository,
) {
    fun handle(command: DeleteScheduledActionCommand) {
        nextActionRepository.deleteNextAction(command.id)
    }
}

data class DeleteScheduledActionCommand(
    val id: UUID,
)
