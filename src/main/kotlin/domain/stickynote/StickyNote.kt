package com.example.domain.stickynote

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

@Serializable
data class StickyNote(
    val concern: String,
    val createdAt: LocalDateTime,
)