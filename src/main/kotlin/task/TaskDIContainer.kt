package com.example.task

import com.example.task.controller.CreateTaskRequestHandler
import com.example.task.dataaccess.TaskDefinitionRepositoryImpl
import com.example.task.entity.TaskDefinitionFactory
import com.example.task.entity.TaskDefinitionRepository
import com.example.task.usecase.CreateTaskDefinition
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

object TaskDIContainer {
    fun defineModule(): Module =
        module {
            singleOf(::TaskDefinitionRepositoryImpl) bind TaskDefinitionRepository::class
            singleOf(::CreateTaskDefinition)
            singleOf(::TaskDefinitionFactory)
            singleOf(::CreateTaskRequestHandler)
        }
}
