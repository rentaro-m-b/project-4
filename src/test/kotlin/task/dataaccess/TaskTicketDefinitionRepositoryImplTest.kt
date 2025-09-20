package task.dataaccess

import com.example.DBSettings
import com.example.common.DSLContextProvider.provideDSLContext
import com.example.common.DataSourceProvider.provideDataSource
import com.example.db.tables.TaskTicketDefinitions.Companion.TASK_TICKET_DEFINITIONS
import com.example.db.tables.records.TaskTicketDefinitionsRecord
import com.example.task.dataaccess.TaskTicketDefinitionRepositoryImpl
import com.example.task.entity.TaskTicketDefinition
import io.kotest.core.spec.style.FunSpec
import io.kotest.koin.KoinExtension
import io.kotest.matchers.shouldBe
import org.flywaydb.core.Flyway
import org.jooq.DSLContext
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.inject
import org.testcontainers.containers.PostgreSQLContainer
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.UUID
import javax.sql.DataSource

class TaskTicketDefinitionRepositoryImplTest :
    FunSpec(),
    KoinTest {
    private val postgres = PostgreSQLContainer("postgres:16-alpine")

    init {
        beforeSpec {
            postgres.start()

            val settings =
                DBSettings(
                    url = postgres.jdbcUrl,
                    user = postgres.username,
                    password = postgres.password,
                    driver = postgres.driverClassName,
                    maxPoolSize = 10,
                )
            val testModule =
                module {
                    single { settings } bind DBSettings::class
                    singleOf(::provideDataSource) bind DataSource::class
                    singleOf(::provideDSLContext) bind DSLContext::class
                    singleOf(::TaskTicketDefinitionRepositoryImpl)
                }
            extensions(KoinExtension(testModule))

            Flyway
                .configure()
                .dataSource(postgres.jdbcUrl, postgres.username, postgres.password)
                .locations("classpath:db/migration")
                .load()
                .migrate()
        }

        afterSpec {
            postgres.stop()
        }

        val target by inject<TaskTicketDefinitionRepositoryImpl>()

        test("normal: create task ticket definition") {
            // arrange

            // act
            target.create(
                entity =
                    TaskTicketDefinition(
                        id = UUID.fromString("3ee1f358-690f-4ac7-8eec-8e3be49419df"),
                        description = "「テスト駆動開発」を読む",
                        expected = BigDecimal("5"),
                        unit = "page",
                        createdAt = LocalDateTime.parse("2025-09-17T09:00:00"),
                        updatedAt = LocalDateTime.parse("2025-09-17T09:00:00"),
                    ),
            )

            // assert
            val actual =
                getKoin()
                    .get<DSLContext>()
                    .selectFrom(TASK_TICKET_DEFINITIONS)
                    .where(TASK_TICKET_DEFINITIONS.ID.eq(UUID.fromString("3ee1f358-690f-4ac7-8eec-8e3be49419df")))
                    .fetchOne() ?: error("row not found")

            val expected =
                TaskTicketDefinitionsRecord(
                    id = UUID.fromString("3ee1f358-690f-4ac7-8eec-8e3be49419df"),
                    description = "「テスト駆動開発」を読む",
                    expected = BigDecimal("5").setScale(TASK_TICKET_DEFINITIONS.EXPECTED.dataType.scale()),
                    unit = "page",
                    createdAt = LocalDateTime.parse("2025-09-17T09:00:00"),
                    updatedAt = LocalDateTime.parse("2025-09-17T09:00:00"),
                )
            actual shouldBe expected
        }
    }
}
