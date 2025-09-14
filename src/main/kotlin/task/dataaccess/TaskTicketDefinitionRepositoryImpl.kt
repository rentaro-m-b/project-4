package com.example.task.dataaccess

import com.example.db.tables.TaskTicketDefinitions.Companion.TASK_TICKET_DEFINITIONS
import com.example.task.entity.TaskTicketDefinition
import com.example.task.entity.TaskTicketDefinitionRepository
import org.jooq.DSLContext

class TaskTicketDefinitionRepositoryImpl(
    val dslContext: DSLContext,
) : TaskTicketDefinitionRepository {
    override fun create(entity: TaskTicketDefinition) {
        dslContext
            .insertInto(TASK_TICKET_DEFINITIONS)
            .set(TASK_TICKET_DEFINITIONS.ID, entity.id)
            .set(TASK_TICKET_DEFINITIONS.DESCRIPTION, entity.description)
            .set(TASK_TICKET_DEFINITIONS.EXPECTED, entity.expected)
            .set(TASK_TICKET_DEFINITIONS.UNIT, entity.unit)
            .set(TASK_TICKET_DEFINITIONS.CREATED_AT, entity.createdAt)
            .set(TASK_TICKET_DEFINITIONS.UPDATED_AT, entity.updatedAt)
            .execute()
    }
}
