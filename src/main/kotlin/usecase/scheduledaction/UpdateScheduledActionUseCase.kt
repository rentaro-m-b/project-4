package com.example.usecase.scheduledaction

import com.example.domain.nextaction.NextAction
import com.example.domain.nextaction.NextActionRepository
import java.util.UUID

class UpdateScheduledActionUseCase(
    val nextActionRepository: NextActionRepository,
) {
    fun handle(command: UpdateNextActionCommand) {
        val currentNextAction = nextActionRepository.fetchNextAction(command.id)
        if (currentNextAction == null) return

        val nextAction =
            NextAction.create(
                id = command.id,
                description = command.description,
                createdAt = currentNextAction.createdAt,
            )
        nextActionRepository.updateNextAction(nextAction)
    }
}

data class UpdateNextActionCommand(
    val id: UUID,
    val description: String,
)
