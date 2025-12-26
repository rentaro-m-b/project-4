package com.example.usecase.nextaction

import com.example.domain.nextaction.NextAction
import com.example.domain.nextaction.NextActionRepository

class ListNextActionsUseCase(
    private val nextActionRepository: NextActionRepository,
) {
    fun handle(): List<NextAction> = nextActionRepository.listNextActions()
}
