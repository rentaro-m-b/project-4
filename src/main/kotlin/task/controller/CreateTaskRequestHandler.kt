package com.example.task.controller

import com.example.task.usecase.CreateTaskTicketDefinitionCommand
import com.example.task.usecase.CreateTaskTicketDefinitionUseCase
import kotlinx.serialization.Serializable
import java.math.BigDecimal
import java.util.UUID

class CreateTaskRequestHandler(
    val useCase: CreateTaskTicketDefinitionUseCase,
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
) {
    fun toCommand(): CreateTaskTicketDefinitionCommand =
        CreateTaskTicketDefinitionCommand(
            description = description,
            expected = BigDecimal(expected),
            unit = unit,
        )
}
