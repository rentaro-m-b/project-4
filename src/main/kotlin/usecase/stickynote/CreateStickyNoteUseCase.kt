package com.example.usecase.stickynote

import com.example.domain.stickynote.StickyNote
import com.example.domain.stickynote.StickyNoteRepository
import jakarta.inject.Singleton
import java.time.LocalDateTime
import java.util.UUID

@Singleton
class CreateStickyNoteUseCase(
    private val stickyNoteRepository: StickyNoteRepository,
    private val idGenerator: IdGenerator,
) {
    fun handle(command: CreateStickyNoteCommand): UUID {
        val stickyNote =
            StickyNote.create(
                id = idGenerator.generateId(),
                concern = command.concern,
                imageKey = "",
                createdAt = LocalDateTime.now(),
            )
        stickyNoteRepository.createStickyNote(stickyNote)
        return stickyNote.id
    }
}

data class CreateStickyNoteCommand(
    val concern: String,
    val path: String,
)

@Singleton
class IdGenerator {
    fun generateId(): UUID = UUID.randomUUID()
}
