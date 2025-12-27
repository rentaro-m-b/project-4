package com.example.usecase.scheduledaction

import com.example.domain.shceduledaction.ScheduledAction
import com.example.domain.shceduledaction.ScheduledActionRepository
import com.example.domain.stickynote.StickyNoteRepository
import com.example.usecase.common.CurrentStickyNoteNotFoundException
import org.slf4j.LoggerFactory
import java.time.LocalDateTime
import java.util.UUID

class CreateScheduledActionUseCase(
    private val scheduledActionRepository: ScheduledActionRepository,
    private val stickyNoteRepository: StickyNoteRepository,
) {
    private val log = LoggerFactory.getLogger(CreateScheduledActionUseCase::class.java)

    fun handle(command: CreateScheduledActionCommand): Result<UUID> {
        if (stickyNoteRepository.fetchStickyNote(command.stickyNoteId) == null) {
            log.warn("sticky note not found : ${command.stickyNoteId}")
            return Result.failure(CurrentStickyNoteNotFoundException("sticky note not found : ${command.stickyNoteId}"))
        }
        val scheduledAction =
            ScheduledAction.create(
                id = UUID.randomUUID(),
                description = command.description,
                startsAt = command.startsAt,
                endsAt = command.endsAt,
                createdAt = LocalDateTime.now(),
            )
        scheduledActionRepository.createScheduledAction(scheduledAction)

        return Result.success(scheduledAction.id)
    }
}

data class CreateScheduledActionCommand(
    val stickyNoteId: UUID,
    val startsAt: LocalDateTime,
    val endsAt: LocalDateTime,
    val description: String,
)
