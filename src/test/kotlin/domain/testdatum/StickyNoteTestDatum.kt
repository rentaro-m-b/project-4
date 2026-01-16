package domain.testdatum

import com.example.domain.stickynote.StickyNote
import java.time.LocalDateTime
import java.util.UUID

object StickyNoteTestDatum {
    val STICKY_NOTE_1 =
        StickyNote.create(
            id = UUID.fromString("ae95e722-253d-4fde-94f7-598da746cf0c"),
            concern = "wanting to submit to illustration contests",
            imageKey = "",
            createdAt = LocalDateTime.parse("2025-01-01T00:00:00"),
        )

    val STICKY_NOTE_2 =
        StickyNote.create(
            id = UUID.fromString("36baaf2f-e621-4db4-b26a-9dda2db5cb29"),
            concern = "wanting to get better at drawing",
            imageKey = "",
            createdAt = LocalDateTime.parse("2025-01-01T00:00:01"),
        )

    val STICKY_NOTE_3 =
        StickyNote.create(
            id = UUID.fromString("3a7c31c1-765b-4486-a05f-eefbee300be4"),
            concern = "worrying about not losing weight",
            imageKey = "",
            createdAt = LocalDateTime.parse("2025-01-01T00:00:02"),
        )

    val STICKY_NOTE_4 =
        StickyNote.create(
            id = UUID.fromString("8df1df03-5e9d-4a2a-aec3-96060d27727d"),
            concern = "to read books",
            imageKey = "",
            createdAt = LocalDateTime.parse("2025-01-01T00:00:03"),
        )
}
