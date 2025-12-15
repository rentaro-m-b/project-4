package com.example.domain.shceduledaction

import java.util.UUID

interface ScheduledActionRepository {
    fun listScheduledActions(): List<ScheduledAction>

    fun fetchScheduledAction(id: UUID): ScheduledAction?

    fun createScheduledAction(scheduledAction: ScheduledAction)

    fun updateScheduledAction(scheduledAction: ScheduledAction)

    fun deleteScheduledAction(id: UUID)
}
