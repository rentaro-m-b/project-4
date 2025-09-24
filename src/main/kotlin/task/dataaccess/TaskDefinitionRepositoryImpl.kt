package com.example.task.dataaccess

import com.example.db.tables.TaskTicketDefinitions.Companion.TASK_TICKET_DEFINITIONS
import com.example.task.entity.TaskDefinition
import com.example.task.entity.TaskDefinitionRepository
import org.jooq.DSLContext

class TaskDefinitionRepositoryImpl(
    val dslContext: DSLContext,
) : TaskDefinitionRepository {
    override fun create(entity: TaskDefinition) {
        dslContext
            .insertInto(TASK_TICKET_DEFINITIONS)
            .set(TASK_TICKET_DEFINITIONS.ID, entity.id)
            .set(TASK_TICKET_DEFINITIONS.DESCRIPTION, entity.description)
            .set(TASK_TICKET_DEFINITIONS.EXPECTED, entity.expected)
            .set(TASK_TICKET_DEFINITIONS.UNIT, entity.unit)
            .set(TASK_TICKET_DEFINITIONS.CYCLE_PER_DAYS, entity.cyclePerDays)
            .set(TASK_TICKET_DEFINITIONS.CREATED_AT, entity.createdAt)
            .set(TASK_TICKET_DEFINITIONS.UPDATED_AT, entity.updatedAt)
            .execute()
    }
}
