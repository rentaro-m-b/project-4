package com.example.infra.nextaction

import com.example.domain.nextaction.NextAction
import com.example.domain.nextaction.NextActionRepository
import com.example.tables.NextActions.NEXT_ACTIONS
import com.example.tables.records.NextActionsRecord
import org.jooq.DSLContext
import java.util.*

class NextActionRepositoryImpl(val dslContext: DSLContext): NextActionRepository {
    override fun listNextActions(): List<NextAction> {
        val records = dslContext.selectFrom(NEXT_ACTIONS).fetch().toList()
        return records.map { it.toEntity() }
    }

    override fun fetchNextAction(id: UUID): NextAction? {
        val record = dslContext.selectFrom(NEXT_ACTIONS).where(NEXT_ACTIONS.ID.eq(id)).fetchOne()
        return record?.toEntity()
    }

    override fun createNextAction(nextAction: NextAction) {
        val record = dslContext.newRecord(NEXT_ACTIONS).apply {
            id = UUID.randomUUID()
            description = nextAction.description
            createdAt = nextAction.createdAt
        }
        record.store()
    }

    override fun updateNextAction(nextAction: NextAction) {
        val record = dslContext.selectFrom(NEXT_ACTIONS).where(NEXT_ACTIONS.ID.eq(nextAction.id)).fetchOne()
        if (record != null) {
            record.description = nextAction.description
            record.store()
        }
    }

    override fun deleteNextAction(id: UUID) {
        val record = dslContext.selectFrom(NEXT_ACTIONS).where(NEXT_ACTIONS.ID.eq(id)).fetchOne()
        record?.delete()
    }
}

fun NextActionsRecord.toEntity(): NextAction {
    return NextAction.create(
        id = id,
        description = description,
        createdAt = createdAt,
    )
}
