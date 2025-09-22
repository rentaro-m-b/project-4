package com.example.task

import com.example.task.controller.CreateTaskRequestHandler
import com.example.task.dataaccess.TaskTicketDefinitionRepositoryImpl
import com.example.task.entity.TaskTicketDefinitionFactory
import com.example.task.entity.TaskTicketDefinitionRepository
import com.example.task.usecase.CreateTaskTicketDefinition
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

object TaskDIContainer {
    fun defineModule(): Module =
        module {
            singleOf(::TaskTicketDefinitionRepositoryImpl) bind TaskTicketDefinitionRepository::class
            singleOf(::CreateTaskTicketDefinition)
            singleOf(::TaskTicketDefinitionFactory)
            singleOf(::CreateTaskRequestHandler)
        }
}
