package ut.infra

import com.example.domain.stickynote.StickyNote
import com.example.infra.stickynote.StickyNoteRepositoryImpl
import com.github.database.rider.core.api.dataset.DataSet
import com.github.database.rider.core.api.dataset.ExpectedDataSet
import com.github.database.rider.junit5.api.DBRider
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import domain.testdatum.StickyNoteTestDatum.STICKY_NOTE_1
import domain.testdatum.StickyNoteTestDatum.STICKY_NOTE_2
import domain.testdatum.StickyNoteTestDatum.STICKY_NOTE_3
import domain.testdatum.StickyNoteTestDatum.STICKY_NOTE_4
import org.jooq.SQLDialect
import org.jooq.impl.DSL
import org.junit.jupiter.api.Nested
import java.time.LocalDateTime
import java.util.UUID
import kotlin.test.Test
import kotlin.test.assertEquals

@DBRider
class StickyNoteRepositoryImplTest {
    private val config = HikariConfig()
    private val dataSource: HikariDataSource

    init {
        config.jdbcUrl = "jdbc:postgresql://localhost:54332/main"
        config.username = "montre"
        config.password = "P@ssw0rd"
        dataSource = HikariDataSource(config)
    }

    private val target = StickyNoteRepositoryImpl(DSL.using(dataSource.connection, SQLDialect.POSTGRES))

    @Nested
    inner class ListStickyNotes {
        @Test
        @DataSet(value = ["datasets/setup/stickyNotes.yaml"], cleanBefore = true)
        fun normal() {
            // setup

            // execute
            val actual = target.listStickyNotes()

            // assert
            val expected =
                listOf(
                    STICKY_NOTE_1,
                    STICKY_NOTE_2,
                    STICKY_NOTE_3,
                    STICKY_NOTE_4,
                )

            assertEquals(expected, actual)
        }
    }

    @Nested
    inner class CreateStickyNote {
        @Test
        @DataSet(value = ["datasets/setup/stickyNotes.yaml"], cleanBefore = true)
        @ExpectedDataSet(
            value = ["datasets/expected/stickynote/createStickyNote.yaml"],
            orderBy = ["created_at"],
        )
        fun normal() {
            // setup

            // execute
            val actual =
                target.createStickyNote(
                    StickyNote.create(
                        id = UUID.fromString("7147553a-0338-4ee4-b9e8-ddea8b6bc311"),
                        concern = "wanting to have happiness",
                        createdAt = LocalDateTime.parse("2025-02-01T00:00:00"),
                    ),
                )

            // assert
            val expected = Unit
            assertEquals(expected, actual)
        }
    }

    @Nested
    inner class UpdateStickyNote {
        @Test
        @DataSet(value = ["datasets/setup/stickyNotes.yaml"], cleanBefore = true)
        @ExpectedDataSet(
            value = ["datasets/expected/stickynote/updateStickyNote.yaml"],
            orderBy = ["created_at"],
        )
        fun normal() {
            // setup

            // execute
            val actual =
                target.updateStickyNote(
                    StickyNote.create(
                        id = STICKY_NOTE_1.id,
                        concern = "wanting to have happiness",
                        createdAt = STICKY_NOTE_1.createdAt,
                    ),
                )

            // assert
            val expected = Unit
            assertEquals(expected, actual)
        }
    }

    @Nested
    inner class DeleteStickyNote {
        @Test
        @DataSet(value = ["datasets/setup/stickyNotes.yaml"], cleanBefore = true)
        @ExpectedDataSet(
            value = ["datasets/expected/stickynote/deleteStickyNote.yaml"],
            orderBy = ["created_at"],
        )
        fun normal() {
            // setup

            // execute
            val actual = target.deleteStickyNote(STICKY_NOTE_1.id)

            // assert
            val expected = Unit
            assertEquals(expected, actual)
        }
    }
}
