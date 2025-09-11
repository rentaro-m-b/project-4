package com.example.task

import com.example.module
import com.example.task.dataaccess.TaskTicketDefinitionRepositoryImpl
import com.example.task.entity.TaskTicketDefinitionRepository
import io.ktor.server.application.Application
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.ktor.plugin.koinModule

fun Application.taskModule() {
    koinModule {
        singleOf(::TaskTicketDefinitionRepositoryImpl) bind TaskTicketDefinitionRepository::class
    }
}
