package com.example.domain.stickynote

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.time.LocalDateTime

@Serializable
data class StickyNote(
    val concern: String,
    @Contextual
    val createdAt: LocalDateTime,
)
