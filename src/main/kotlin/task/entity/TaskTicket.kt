package com.example.task.entity

import java.math.BigDecimal
import java.util.UUID

data class TaskTicket(
    val id: UUID,
    val taskTicketDefinitionId: UUID,
    val actual: BigDecimal,
    val timeBox: TimeBox,
)
