package com.example.task.usecase

fun interface CreateTaskTicketDefinitionUseCase {
    fun createTaskTicketDefinition(command: CreateTaskTicketDefinitionCommand): String
}

data class CreateTaskTicketDefinitionCommand(
    val description: String,
    val expected: Double,
    val unit: String,
)
