package com.example.task.entity

import com.example.task.entity.ScheduleRule.DAILY
import com.example.task.entity.ScheduleRule.MONTHLY
import com.example.task.entity.ScheduleRule.WEEKLY
import java.time.Clock
import java.time.Duration
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId
import java.util.*

data class Setting(
    val id: UUID,
    val taskTicketDefinition: UUID,
    val scheduleRule: ScheduleRule,
    val start: LocalTime,
    val duration: Duration,
) {
    // REASON: タスクの遂行時間の開始または終了で作成するため
    // 次回のタスクに何かしらのメモができるようにする、ということであれば、遂行時間の開始で作成するのが良い
    fun createStart(): LocalDateTime {
        val clock = Clock.system(ZoneId.of("UTC"));
        val now = LocalDate.now(clock)
        val nextDay = when(scheduleRule) {
            DAILY -> now.plusDays(1)
            WEEKLY -> now.plusWeeks(1)
            MONTHLY -> now.plusMonths(1)
        }
        val result = nextDay.atTime(start)

        return result
    }

    // REASON: タスクの遂行時間の開始または終了で作成するため
    fun createEnd(): LocalDateTime {
        val clock = Clock.system(ZoneId.of("UTC"));
        val now = LocalDate.now(clock)
        val nextDay = when(scheduleRule) {
            DAILY -> now.plusDays(1)
            WEEKLY -> now.plusWeeks(1)
            MONTHLY -> now.plusMonths(1)
        }
        val result = nextDay.atTime(start).plus(duration)

        return result
    }
}
