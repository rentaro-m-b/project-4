package com.example.domain.nextaction

import java.time.LocalDateTime
import java.util.UUID

class NextAction private constructor(
    val id: UUID,
    val description: String,
    val createdAt: LocalDateTime,
) {
    companion object {
        // TODO: 作成フローは今のところ、付箋からネクストアクションに変化する、という流れのみである
        // TODO: その流れを扱うクラスが別途必要である
        fun create(
            id: UUID,
            description: String,
            createdAt: LocalDateTime,
        ): NextAction =
            NextAction(
                id = id,
                description = description,
                createdAt = createdAt,
            )
    }

    override operator fun equals(other: Any?): Boolean = other is NextAction && other.id == id

    override fun hashCode(): Int = id.hashCode()
}
