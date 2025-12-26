package com.example.usecase.nextaction

import com.example.domain.nextaction.NextAction
import com.example.domain.nextaction.NextActionRepository
import com.example.usecase.common.CurrentNextActionNotFoundException
import java.util.UUID

class UpdateNextActionUseCase(
    private val nextActionRepository: NextActionRepository,
) {
    fun handle(command: UpdateNextActionCommand): Result<Unit> {
        val currentNextAction = nextActionRepository.fetchNextAction(command.id)
        if (currentNextAction == null) {
            return Result.failure(CurrentNextActionNotFoundException("next action not found : ${command.id}"))
        }

        val nextAction =
            NextAction.create(
                id = command.id,
                description = command.description,
                createdAt = currentNextAction.createdAt,
            )
        nextActionRepository.updateNextAction(nextAction)

        return Result.success(Unit)
    }
}

data class UpdateNextActionCommand(
    val id: UUID,
    val description: String,
)
