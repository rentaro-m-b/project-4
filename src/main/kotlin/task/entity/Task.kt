package com.example.task.entity

import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.UUID

data class Task(
    val id: UUID,
    val taskDefinitionId: UUID,
    val actual: BigDecimal,
    val dueDate: DueDate,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
)
