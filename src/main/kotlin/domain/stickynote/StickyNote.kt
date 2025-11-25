package com.example.domain.stickynote

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.time.LocalDateTime

data class StickyNote(
    val concern: String,
    val createdAt: LocalDateTime,
)
