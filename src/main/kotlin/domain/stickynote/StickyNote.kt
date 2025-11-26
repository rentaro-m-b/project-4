package com.example.domain.stickynote

import java.time.LocalDateTime

data class StickyNote(
    val concern: String,
    val createdAt: LocalDateTime,
)
