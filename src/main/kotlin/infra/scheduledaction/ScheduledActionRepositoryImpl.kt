package com.example.infra.scheduledaction

import com.example.domain.shceduledaction.ScheduledAction
import com.example.domain.shceduledaction.ScheduledActionRepository
import com.example.tables.ScheduledActions.SCHEDULED_ACTIONS
import com.example.tables.records.ScheduledActionsRecord
import org.jooq.DSLContext
import java.util.UUID

class ScheduledActionRepositoryImpl(
    val dslContext: DSLContext,
) : ScheduledActionRepository {
    override fun listScheduledActions(): List<ScheduledAction> {
        val records = dslContext.selectFrom(SCHEDULED_ACTIONS).fetch().toList()
        return records.map { it.toEntity() }
    }

    override fun fetchScheduledAction(id: UUID): ScheduledAction? {
        val record = dslContext.selectFrom(SCHEDULED_ACTIONS).where(SCHEDULED_ACTIONS.ID.eq(id)).fetchOne()
        return record?.toEntity()
    }

    override fun createScheduledAction(scheduledAction: ScheduledAction) {
        val record =
            dslContext.newRecord(SCHEDULED_ACTIONS).apply {
                id = UUID.randomUUID()
                description = scheduledAction.description
                startsAt = scheduledAction.startsAt
                endsAt = scheduledAction.endsAt
                createdAt = scheduledAction.createdAt
            }
        record.store()
    }

    override fun updateScheduledAction(scheduledAction: ScheduledAction) {
        val record = dslContext.selectFrom(SCHEDULED_ACTIONS).where(SCHEDULED_ACTIONS.ID.eq(scheduledAction.id)).fetchOne()
        if (record != null) {
            record.description = scheduledAction.description
            record.startsAt = scheduledAction.startsAt
            record.endsAt = scheduledAction.endsAt
            record.store()
        }
    }

    override fun deleteScheduledAction(id: UUID) {
        val record = dslContext.selectFrom(SCHEDULED_ACTIONS).where(SCHEDULED_ACTIONS.ID.eq(id)).fetchOne()
        record?.delete()
    }
}

fun ScheduledActionsRecord.toEntity(): ScheduledAction =
    ScheduledAction.create(
        id = id,
        description = description,
        startsAt = startsAt,
        endsAt = endsAt,
        createdAt = createdAt,
    )
