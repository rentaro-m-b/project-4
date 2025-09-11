package com.example.task.dataaccess

import com.example.task.entity.TaskTicketDefinition
import com.example.task.entity.TaskTicketDefinitionRepository

class TaskTicketDefinitionRepositoryImpl : TaskTicketDefinitionRepository {
    override fun create(entity: TaskTicketDefinition) {
        // TODO: JOOQを取り入れてから
        println("created")
    }
}
