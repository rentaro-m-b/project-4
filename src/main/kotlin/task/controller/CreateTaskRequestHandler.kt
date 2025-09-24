package com.example.task.controller

import com.example.task.usecase.CreateTaskDefinition
import com.example.task.usecase.CreateTaskDefinitionCommand
import kotlinx.serialization.Serializable
import java.math.BigDecimal
import java.util.UUID

class CreateTaskRequestHandler(
    val useCase: CreateTaskDefinition,
) {
    fun handle(request: CreateTaskRequest): UUID {
        val taskTicketDefinitionId = useCase.execute(request.toCommand())

        return taskTicketDefinitionId
    }
}

@Serializable
data class CreateTaskRequest(
    val description: String,
    val expected: String,
    val unit: String,
    val cyclePerDays: Short,
) {
    fun toCommand(): CreateTaskDefinitionCommand =
        CreateTaskDefinitionCommand(
            description = description,
            expected = BigDecimal(expected),
            unit = unit,
            cyclePerDays = cyclePerDays,
        )
}
