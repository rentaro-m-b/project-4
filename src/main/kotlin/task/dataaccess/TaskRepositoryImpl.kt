package com.example.task.dataaccess

import com.example.db.tables.references.TASKS
import com.example.task.entity.Task
import com.example.task.entity.TaskRepository
import org.jooq.DSLContext

class TaskRepositoryImpl(
    val dslContext: DSLContext,
) : TaskRepository {
    override fun create(entity: Task) {
        dslContext
            .insertInto(TASKS)
            .set(TASKS.ID, entity.id)
            .set(TASKS.TASK_DEFINITION_ID, entity.taskDefinitionId)
            .set(TASKS.ACTUAL, entity.actual)
            .set(TASKS.DUE_DATE, entity.dueDate.dateTime)
            .set(TASKS.CREATED_AT, entity.createdAt)
            .set(TASKS.UPDATED_AT, entity.updatedAt)
            .execute()
    }
}
