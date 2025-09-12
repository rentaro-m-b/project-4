package com.example.task.usecase

class CreateTaskTicketDefinitionUseCase {
    fun execute(command: CreateTaskTicketDefinitionCommand): String = ""
}

data class CreateTaskTicketDefinitionCommand(
    val description: String,
    val expected: Double,
    val unit: String,
)
