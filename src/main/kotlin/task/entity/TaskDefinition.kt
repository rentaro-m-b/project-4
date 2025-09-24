package com.example.task.entity

import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.UUID

data class TaskDefinition(
    val id: UUID,
    val description: String,
    val unit: String,
    val expected: BigDecimal,
    val cyclePerDays: Short,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
)
