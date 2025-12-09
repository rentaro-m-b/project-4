package com.example.domain.nextaction

import java.util.UUID

interface NextActionRepository {
    fun listNextActions(): List<NextAction>

    fun fetchNextAction(id: UUID): NextAction?

    fun createNextAction(nextAction: NextAction)

    fun updateNextAction(nextAction: NextAction)

    fun deleteNextAction(id: UUID)
}
