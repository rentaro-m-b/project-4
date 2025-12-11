package com.example.controller.nextaction

import com.example.domain.nextaction.NextAction
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.time.LocalDateTime

@Serializable
data class ListNextActionsResponse(
    val description: String,
    @Contextual
    val createdAt: LocalDateTime,
) {
    companion object {
        fun of(nextAction: NextAction): ListNextActionsResponse =
            ListNextActionsResponse(
                description = nextAction.description,
                createdAt = nextAction.createdAt,
            )
    }
}