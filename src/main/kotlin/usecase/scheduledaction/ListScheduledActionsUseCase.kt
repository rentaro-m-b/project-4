package com.example.usecase.scheduledaction

import com.example.domain.nextaction.NextAction
import com.example.domain.nextaction.NextActionRepository
import com.example.domain.shceduledaction.ScheduledAction
import com.example.domain.shceduledaction.ScheduledActionRepository

class ListScheduledActionsUseCase(
    val scheduledActionRepository: ScheduledActionRepository,
) {
    fun handle(): List<ScheduledAction> = scheduledActionRepository.listScheduledActions()
}
