package com.example.task.dataaccess

import com.example.db.tables.TaskDefinitions.Companion.TASK_DEFINITIONS
import com.example.task.entity.TaskDefinition
import com.example.task.entity.TaskDefinitionRepository
import org.jooq.DSLContext

class TaskDefinitionRepositoryImpl(
    val dslContext: DSLContext,
) : TaskDefinitionRepository {
    override fun create(entity: TaskDefinition) {
        dslContext
            .insertInto(TASK_DEFINITIONS)
            .set(TASK_DEFINITIONS.ID, entity.id)
            .set(TASK_DEFINITIONS.DESCRIPTION, entity.description)
            .set(TASK_DEFINITIONS.EXPECTED, entity.expected)
            .set(TASK_DEFINITIONS.UNIT, entity.unit)
            .set(TASK_DEFINITIONS.CYCLE_PER_DAYS, entity.cyclePerDays)
            .set(TASK_DEFINITIONS.CREATED_AT, entity.createdAt)
            .set(TASK_DEFINITIONS.UPDATED_AT, entity.updatedAt)
            .execute()
    }
}
