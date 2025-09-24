package com.example.task.entity

import java.math.BigDecimal
import java.util.UUID

data class Task(
    val id: UUID,
    val taskDefinitionId: UUID,
    val actual: BigDecimal,
    val dueDate: DueDate,
)
