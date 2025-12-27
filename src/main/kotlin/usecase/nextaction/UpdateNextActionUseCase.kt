package com.example.usecase.nextaction

import com.example.domain.nextaction.NextAction
import com.example.domain.nextaction.NextActionRepository
import com.example.usecase.common.CurrentNextActionNotFoundException
import org.slf4j.LoggerFactory
import java.util.UUID

class UpdateNextActionUseCase(
    private val nextActionRepository: NextActionRepository,
) {
    private val log = LoggerFactory.getLogger(UpdateNextActionUseCase::class.java)

    fun handle(command: UpdateNextActionCommand): Result<Unit> {
        val currentNextAction = nextActionRepository.fetchNextAction(command.id)
        if (currentNextAction == null) {
            log.warn("next action not found : ${command.id}")
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
