package ut.usecase

import com.example.domain.shceduledaction.ScheduledAction
import com.example.domain.shceduledaction.ScheduledActionRepository
import com.example.domain.stickynote.StickyNote
import com.example.domain.stickynote.StickyNoteRepository
import com.example.usecase.scheduledaction.ListScheduledActionsUseCase
import com.example.usecase.stickynote.ListStickyNotesUseCase
import io.mockk.every
import io.mockk.mockk
import java.time.LocalDateTime
import java.util.UUID
import kotlin.test.Test
import kotlin.test.assertEquals

class ListScheduledActionsUseCaseTest {
    val scheduledActionsRepository = mockk<ScheduledActionRepository>()
    private val target = ListScheduledActionsUseCase(scheduledActionsRepository)

    @Test
    fun normal() {
        // setup
        every { scheduledActionsRepository.listScheduledActions() } returns
            listOf(
                ScheduledAction.create(
                    id = UUID.fromString("af5307cb-5147-46ad-affa-92be36a67645"),
                    description = "practice drawing for 10 minutes",
                    startsAt = LocalDateTime.parse("2025-02-01T12:00:00"),
                    endsAt = LocalDateTime.parse("2025-02-01T13:00:00"),
                    createdAt = LocalDateTime.parse("2025-01-01T00:00:00"),
                ),
                ScheduledAction.create(
                    id = UUID.fromString("63bc709a-6d1c-4e80-8639-755f48e284d6"),
                    description = "collect five reference materials",
                    startsAt = LocalDateTime.parse("2025-02-01T12:00:01"),
                    endsAt = LocalDateTime.parse("2025-02-01T13:00:01"),
                    createdAt = LocalDateTime.parse("2025-01-01T00:00:01"),
                ),
                ScheduledAction.create(
                    id = UUID.fromString("da48c76d-1ae3-44d1-86b6-dc2b6206b761"),
                    description = "decide on a theme for the painting",
                    startsAt = LocalDateTime.parse("2025-02-01T12:00:02"),
                    endsAt = LocalDateTime.parse("2025-02-01T13:00:02"),
                    createdAt = LocalDateTime.parse("2025-01-01T00:00:02"),
                ),
                ScheduledAction.create(
                    id = UUID.fromString("83f4b2cb-f383-4ef0-983e-3c5ca57ae809"),
                    description = "decide which contest to submit to",
                    startsAt = LocalDateTime.parse("2025-02-01T12:00:03"),
                    endsAt = LocalDateTime.parse("2025-02-01T13:00:03"),
                    createdAt = LocalDateTime.parse("2025-01-01T00:00:03"),
                ),
            )

        // execute
        val actual = target.handle()

        // assert
        val expected =
            listOf(
                ScheduledAction.create(
                    id = UUID.fromString("af5307cb-5147-46ad-affa-92be36a67645"),
                    description = "practice drawing for 10 minutes",
                    startsAt = LocalDateTime.parse("2025-02-01T12:00:00"),
                    endsAt = LocalDateTime.parse("2025-02-01T13:00:00"),
                    createdAt = LocalDateTime.parse("2025-01-01T00:00:00"),
                ),
                ScheduledAction.create(
                    id = UUID.fromString("63bc709a-6d1c-4e80-8639-755f48e284d6"),
                    description = "collect five reference materials",
                    startsAt = LocalDateTime.parse("2025-02-01T12:00:01"),
                    endsAt = LocalDateTime.parse("2025-02-01T13:00:01"),
                    createdAt = LocalDateTime.parse("2025-01-01T00:00:01"),
                ),
                ScheduledAction.create(
                    id = UUID.fromString("da48c76d-1ae3-44d1-86b6-dc2b6206b761"),
                    description = "decide on a theme for the painting",
                    startsAt = LocalDateTime.parse("2025-02-01T12:00:02"),
                    endsAt = LocalDateTime.parse("2025-02-01T13:00:02"),
                    createdAt = LocalDateTime.parse("2025-01-01T00:00:02"),
                ),
                ScheduledAction.create(
                    id = UUID.fromString("83f4b2cb-f383-4ef0-983e-3c5ca57ae809"),
                    description = "decide which contest to submit to",
                    startsAt = LocalDateTime.parse("2025-02-01T12:00:03"),
                    endsAt = LocalDateTime.parse("2025-02-01T13:00:03"),
                    createdAt = LocalDateTime.parse("2025-01-01T00:00:03"),
                ),
            )

        assertEquals(expected, actual)
    }
}
