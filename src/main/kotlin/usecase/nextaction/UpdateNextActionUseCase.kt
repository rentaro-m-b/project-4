package com.example.usecase.nextaction

import com.example.domain.nextaction.NextAction
import com.example.domain.nextaction.NextActionRepository
import java.util.UUID

class UpdateNextActionUseCase(
    val nextActionRepository: NextActionRepository,
) {
    fun handle(command: UpdateNextActionCommand) {
        val currentNextAction = nextActionRepository.fetchNextAction(command.id)
        // TODO: 以下の処理にて404を返すためにエラーを返させる。Resultを導入して試してみる
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
