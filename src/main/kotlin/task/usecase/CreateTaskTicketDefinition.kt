package com.example.task.usecase

import com.example.task.entity.TaskTicketDefinitionFactory
import com.example.task.entity.TaskTicketDefinitionRepository
import java.math.BigDecimal
import java.util.UUID

class CreateTaskTicketDefinition(
    val repository: TaskTicketDefinitionRepository,
    val factory: TaskTicketDefinitionFactory,
) {
    fun execute(command: CreateTaskTicketDefinitionCommand): UUID {
        val taskTicketDefinition =
            factory.create(
                description = command.description,
                unit = command.unit,
                expected = command.expected,
                cyclePerDays = command.cyclePerDays,
            )
        repository.create(taskTicketDefinition)
        return taskTicketDefinition.id
    }
}

data class CreateTaskTicketDefinitionCommand(
    val description: String,
    val expected: BigDecimal,
    val unit: String,
    val cyclePerDays: Short,
)
