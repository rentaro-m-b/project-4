package com.example.task.entity

import java.time.LocalDateTime
import java.time.ZoneId

data class Schedule(
    val start: LocalDateTime,
    val end: LocalDateTime,
    val zoneId: ZoneId,
)
