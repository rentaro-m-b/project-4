package com.example.usecase.scheduledaction

import com.example.domain.nextaction.NextActionRepository
import java.util.UUID

class DeleteScheduledActionUseCase(
    val nextActionRepository: NextActionRepository,
) {
    fun handle(command: DeleteNextActionCommand) {
        nextActionRepository.deleteNextAction(command.id)
    }
}

data class DeleteNextActionCommand(
    val id: UUID,
)
