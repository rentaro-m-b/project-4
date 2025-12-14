package com.example.controller.scheduledaction

import com.example.domain.nextaction.NextAction
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.time.LocalDateTime

@Serializable
data class ListScheduledActionsResponse(
    val description: String,
    @Contextual
    val createdAt: LocalDateTime,
) {
    companion object {
        fun of(nextAction: NextAction): ListScheduledActionsResponse =
            ListScheduledActionsResponse(
                description = nextAction.description,
                createdAt = nextAction.createdAt,
            )
    }
}
