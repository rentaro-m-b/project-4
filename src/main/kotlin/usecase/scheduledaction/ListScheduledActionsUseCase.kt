package com.example.usecase.scheduledaction

import com.example.domain.nextaction.NextAction
import com.example.domain.nextaction.NextActionRepository

class ListScheduledActionsUseCase(
    val nextActionRepository: NextActionRepository,
) {
    fun handle(): List<NextAction> = nextActionRepository.listNextActions()
}
