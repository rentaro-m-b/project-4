package com.example.usecase.nextaction

import com.example.domain.nextaction.NextAction
import com.example.domain.nextaction.NextActionRepository
import com.example.domain.stickynote.StickyNoteRepository
import com.example.usecase.common.CurrentStickyNoteNotFoundException
import com.example.usecase.stickynote.UpdateStickyNoteUseCase
import org.slf4j.LoggerFactory
import java.time.LocalDateTime
import java.util.UUID

class CreateNextActionUseCase(
    private val nextActionRepository: NextActionRepository,
    private val stickyNoteRepository: StickyNoteRepository,
) {
    private val log = LoggerFactory.getLogger(CreateNextActionUseCase::class.java)

    fun handle(command: CreateNextActionCommand): Result<UUID> {
        if (stickyNoteRepository.fetchStickyNote(command.stickyNoteId) == null) {
            log.warn("sticky note not found : ${command.stickyNoteId}")
            return Result.failure(CurrentStickyNoteNotFoundException("sticky note not found : ${command.stickyNoteId}"))
        }
        val nextAction =
            NextAction.create(
                id = UUID.randomUUID(),
                description = command.description,
                createdAt = LocalDateTime.now(),
            )
        nextActionRepository.createNextAction(nextAction)
        return Result.success(nextAction.id)
    }
}

data class CreateNextActionCommand(
    val stickyNoteId: UUID,
    val description: String,
)
