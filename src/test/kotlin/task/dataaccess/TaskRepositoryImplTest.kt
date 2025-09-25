package task.dataaccess

import com.example.DBSettings
import com.example.common.DSLContextProvider.provideDSLContext
import com.example.common.DataSourceProvider.provideDataSource
import com.example.db.tables.records.TasksRecord
import com.example.db.tables.references.TASKS
import com.example.task.dataaccess.TaskRepositoryImpl
import com.example.task.entity.DueDate
import com.example.task.entity.Task
import com.example.task.entity.TaskRepository
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import org.flywaydb.core.Flyway
import org.jooq.DSLContext
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
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

class TaskRepositoryImplTest :
    FunSpec(),
    KoinTest {
    init {
        val target by inject<TaskRepositoryImpl>()
        val postgres = PostgreSQLContainer("postgres:17.6-alpine")

        beforeSpec {
            postgres.start()
            startKoin {
                modules(
                    module {
                        single {
                            DBSettings(
                                url = postgres.jdbcUrl,
                                user = postgres.username,
                                password = postgres.password,
                                driver = postgres.driverClassName,
                                maxPoolSize = 10,
                            )
                        } bind DBSettings::class
                        singleOf(::provideDataSource) bind DataSource::class
                        singleOf(::provideDSLContext) bind DSLContext::class
                        singleOf(::TaskRepositoryImpl) bind TaskRepository::class
                    },
                )
            }
            Flyway
                .configure()
                .dataSource(postgres.jdbcUrl, postgres.username, postgres.password)
                .locations("classpath:db/migration")
                .load()
                .migrate()
        }

        afterSpec {
            stopKoin()
            postgres.stop()
        }

        test("normal: create task") {
            // arrange

            // act
            target.create(
                entity =
                    Task(
                        id = UUID.fromString("3ee1f358-690f-4ac7-8eec-8e3be49419df"),
                        taskDefinitionId = UUID.fromString("3ee1f358-690f-4ac7-8eec-8e3be49419df"),
                        actual = BigDecimal(0),
                        dueDate = DueDate(LocalDateTime.parse("2025-09-17T09:00:00")),
                        createdAt = LocalDateTime.parse("2025-09-17T09:00:00"),
                        updatedAt = LocalDateTime.parse("2025-09-17T09:00:00"),
                    ),
            )

            // assert
            val actual =
                getKoin()
                    .get<DSLContext>()
                    .selectFrom(TASKS)
                    .where(TASKS.ID.eq(UUID.fromString("3ee1f358-690f-4ac7-8eec-8e3be49419df")))
                    .fetchOne() ?: error("row not found")

            val expected =
                TasksRecord(
                    id = UUID.fromString("3ee1f358-690f-4ac7-8eec-8e3be49419df"),
                    taskDefinitionId = UUID.fromString("3ee1f358-690f-4ac7-8eec-8e3be49419df"),
                    actual = BigDecimal(0).setScale(TASKS.ACTUAL.dataType.scale()),
                    dueDate = LocalDateTime.parse("2025-09-17T09:00:00"),
                    createdAt = LocalDateTime.parse("2025-09-17T09:00:00"),
                    updatedAt = LocalDateTime.parse("2025-09-17T09:00:00"),
                )
            actual shouldBe expected
        }
    }
}
