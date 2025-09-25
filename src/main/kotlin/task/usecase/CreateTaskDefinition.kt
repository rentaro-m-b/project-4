package com.example.task.usecase

import com.example.task.entity.DueDate
import com.example.task.entity.TaskDefinitionFactory
import com.example.task.entity.TaskDefinitionRepository
import com.example.task.entity.TaskFactory
import com.example.task.entity.TaskRepository
import java.math.BigDecimal
import java.time.Clock
import java.time.LocalDateTime
import java.util.UUID

class CreateTaskDefinition(
    val taskDefinitionRepository: TaskDefinitionRepository,
    val taskRepository: TaskRepository,
    val taskDefinitionFactory: TaskDefinitionFactory,
    val taskFactory: TaskFactory,
    val clock: Clock,
) {
    fun execute(command: CreateTaskDefinitionCommand): UUID {
        val taskDefinition =
            taskDefinitionFactory.create(
                description = command.description,
                unit = command.unit,
                expected = command.expected,
                cyclePerDays = command.cyclePerDays,
            )
        taskDefinitionRepository.create(taskDefinition)
        val task = taskFactory.create(taskDefinition, DueDate(LocalDateTime.now(clock)))
        taskRepository.create(task)
        return taskDefinition.id
    }
}

data class CreateTaskDefinitionCommand(
    val description: String,
    val expected: BigDecimal,
    val unit: String,
    val cyclePerDays: Short,
)
