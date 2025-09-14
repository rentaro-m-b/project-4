package com.example.task.entity

import java.math.BigDecimal
import java.time.Clock
import java.time.LocalDateTime
import java.util.UUID

class TaskTicketDefinitionFactory(
    val clock: Clock,
) {
    fun create(
        description: String,
        unit: String,
        expected: BigDecimal,
    ): TaskTicketDefinition =
        TaskTicketDefinition(
            id = UUID.randomUUID(),
            description = description,
            unit = unit,
            expected = expected,
            createdAt = LocalDateTime.now(clock),
            updatedAt = LocalDateTime.now(clock),
        )
}
