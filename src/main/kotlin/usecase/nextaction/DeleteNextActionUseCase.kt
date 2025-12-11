package com.example.usecase.nextaction

import com.example.domain.nextaction.NextActionRepository
import java.util.*

class DeleteNextActionUseCase(
    val nextActionRepository: NextActionRepository,
) {
    fun handle(command: DeleteNextActionCommand) {
        nextActionRepository.deleteNextAction(command.id)
    }
}

data class DeleteNextActionCommand(
    val id: UUID,
)
