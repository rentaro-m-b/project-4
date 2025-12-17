package com.example.controller.scheduledaction

import com.example.domain.shceduledaction.ScheduledAction
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.time.LocalDateTime

@Serializable
data class ListScheduledActionsResponse(
    val description: String,
    @Contextual
    val startsAt: LocalDateTime,
    @Contextual
    val endsAt: LocalDateTime,
    @Contextual
    val createdAt: LocalDateTime,
) {
    companion object {
        fun of(scheduledAction: ScheduledAction): ListScheduledActionsResponse =
            ListScheduledActionsResponse(
                description = scheduledAction.description,
                startsAt = scheduledAction.startsAt,
                endsAt = scheduledAction.endsAt,
                createdAt = scheduledAction.createdAt,
            )
    }
}
