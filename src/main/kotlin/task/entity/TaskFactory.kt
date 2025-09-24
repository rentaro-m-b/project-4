package com.example.task.entity

import java.math.BigDecimal
import java.time.Clock
import java.util.UUID

class TaskFactory(
    val clock: Clock,
) {
    fun create(
        taskDefinition: TaskDefinition,
        dueDate: DueDate,
    ): Task =
        Task(
            id = UUID.randomUUID(),
            taskDefinitionId = taskDefinition.id,
            actual = BigDecimal(0),
            dueDate = dueDate,
        )
}
