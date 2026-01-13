package ut.infra

import com.example.infra.stickynote.StickyNoteRepositoryImpl
import com.github.database.rider.core.api.dataset.DataSet
import com.github.database.rider.junit5.api.DBRider
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import domain.testdatum.StickyNoteTestDatum.STICKY_NOTE_1
import domain.testdatum.StickyNoteTestDatum.STICKY_NOTE_2
import domain.testdatum.StickyNoteTestDatum.STICKY_NOTE_3
import domain.testdatum.StickyNoteTestDatum.STICKY_NOTE_4
import org.jooq.SQLDialect
import org.jooq.impl.DSL
import kotlin.test.Test
import kotlin.test.assertEquals

@DBRider
class ListStickyNotesRepositoryImplTest {
    private val config = HikariConfig()
    private val dataSource: HikariDataSource

    init {
        config.jdbcUrl = "jdbc:postgresql://localhost:54332/main"
        config.username = "montre"
        config.password = "P@ssw0rd"
        dataSource = HikariDataSource(config)
    }

    private val target = StickyNoteRepositoryImpl(DSL.using(dataSource.connection, SQLDialect.POSTGRES))

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
