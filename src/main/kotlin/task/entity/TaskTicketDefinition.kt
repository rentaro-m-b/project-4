package com.example.task.entity

import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.*

data class TaskTicketDefinition(
    val id: UUID,
    val description: String,
    val unit: String,
    val expected: BigDecimal,
) {
    fun createTaskTicket(
        start: LocalDateTime,
        end: LocalDateTime,
    ): TaskTicket {
        return TaskTicket(
            id = UUID.randomUUID(),
            taskTicketDefinitionId = id,
            actual = BigDecimal("0"),
            timeBox = TimeBox(
                start,
                end,
            ),
        )
    }
}
