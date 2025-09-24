package com.example.task.usecase

import com.example.task.entity.DueDate
import com.example.task.entity.Task
import com.example.task.entity.TaskDefinition
import com.example.task.entity.TaskDefinitionFactory
import com.example.task.entity.TaskDefinitionRepository
import com.example.task.entity.TaskFactory
import java.math.BigDecimal
import java.time.Clock
import java.time.LocalDateTime
import java.util.UUID

class CreateTaskDefinition(
    val repository: TaskDefinitionRepository,
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
        repository.create(taskDefinition)
        val task = taskFactory.create(taskDefinition, DueDate(LocalDateTime.now(clock)))
        return taskDefinition.id
    }

    private fun TaskDefinition.createTaskTicket(dueDate: DueDate): Task =
        Task(
            id = UUID.randomUUID(),
            taskDefinitionId = id,
            actual = BigDecimal(0),
            dueDate = dueDate,
        )
}

data class CreateTaskDefinitionCommand(
    val description: String,
    val expected: BigDecimal,
    val unit: String,
    val cyclePerDays: Short,
)
