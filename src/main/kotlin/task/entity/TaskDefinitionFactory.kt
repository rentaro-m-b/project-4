package com.example.task.entity

import java.math.BigDecimal
import java.time.Clock
import java.time.LocalDateTime
import java.util.UUID

class TaskDefinitionFactory(
    val clock: Clock,
) {
    fun create(
        description: String,
        unit: String,
        expected: BigDecimal,
        cyclePerDays: Short,
    ): TaskDefinition =
        TaskDefinition(
            id = UUID.randomUUID(),
            description = description,
            unit = unit,
            expected = expected,
            cyclePerDays = cyclePerDays,
            createdAt = LocalDateTime.now(clock),
            updatedAt = LocalDateTime.now(clock),
        )
}
