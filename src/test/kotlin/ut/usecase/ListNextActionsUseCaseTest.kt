package ut.usecase

import com.example.domain.nextaction.NextAction
import com.example.domain.nextaction.NextActionRepository
import com.example.domain.stickynote.StickyNote
import com.example.domain.stickynote.StickyNoteRepository
import com.example.usecase.nextaction.ListNextActionsUseCase
import com.example.usecase.stickynote.ListStickyNotesUseCase
import io.mockk.every
import io.mockk.mockk
import java.time.LocalDateTime
import java.util.UUID
import kotlin.test.Test
import kotlin.test.assertEquals

class ListNextActionsUseCaseTest {
    val nextActionRepository = mockk<NextActionRepository>()
    private val target = ListNextActionsUseCase(nextActionRepository)

    @Test
    fun normal() {
        // setup
        every { nextActionRepository.listNextActions() } returns
            listOf(
                NextAction.create(
                    id = UUID.fromString("e574a515-5170-4d76-afc9-da17513dc5d3"),
                    description = "practice drawing for 10 minutes",
                    createdAt = LocalDateTime.parse("2025-01-01T00:00:00"),
                ),
                NextAction.create(
                    id = UUID.fromString("0909127e-69dc-4f7a-82b2-9da21e2cd090"),
                    description = "collect five reference materials",
                    createdAt = LocalDateTime.parse("2025-01-01T00:00:01"),
                ),
                NextAction.create(
                    id = UUID.fromString("117f0003-9f42-4750-9ed2-9356bb8a9887"),
                    description = "decide on a theme for the painting",
                    createdAt = LocalDateTime.parse("2025-01-01T00:00:02"),
                ),
                NextAction.create(
                    id = UUID.fromString("bc562522-7dc8-42bd-a0ee-6cc5ef238e9f"),
                    description = "decide which contest to submit to",
                    createdAt = LocalDateTime.parse("2025-01-01T00:00:03"),
                ),
            )

        // execute
        val actual = target.handle()

        // assert
        val expected =
            listOf(
                NextAction.create(
                    id = UUID.fromString("e574a515-5170-4d76-afc9-da17513dc5d3"),
                    description = "practice drawing for 10 minutes",
                    createdAt = LocalDateTime.parse("2025-01-01T00:00:00"),
                ),
                NextAction.create(
                    id = UUID.fromString("0909127e-69dc-4f7a-82b2-9da21e2cd090"),
                    description = "collect five reference materials",
                    createdAt = LocalDateTime.parse("2025-01-01T00:00:01"),
                ),
                NextAction.create(
                    id = UUID.fromString("117f0003-9f42-4750-9ed2-9356bb8a9887"),
                    description = "decide on a theme for the painting",
                    createdAt = LocalDateTime.parse("2025-01-01T00:00:02"),
                ),
                NextAction.create(
                    id = UUID.fromString("bc562522-7dc8-42bd-a0ee-6cc5ef238e9f"),
                    description = "decide which contest to submit to",
                    createdAt = LocalDateTime.parse("2025-01-01T00:00:03"),
                ),
            )

        assertEquals(expected, actual)
    }
}
