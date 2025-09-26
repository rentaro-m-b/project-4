package task

import com.example.DBSettings
import com.example.common.DSLContextProvider.provideDSLContext
import com.example.common.DataSourceProvider.provideDataSource
import com.example.task.controller.CreateTaskRequestHandler
import com.example.task.dataaccess.TaskRepositoryImpl
import com.example.task.entity.TaskRepository
import com.example.task.usecase.CreateTaskDefinition
import io.kotest.core.spec.style.FunSpec
import io.ktor.server.testing.TestApplication
import org.flywaydb.core.Flyway
import org.jooq.DSLContext
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.testcontainers.containers.PostgreSQLContainer
import javax.sql.DataSource

class TaskIntegrationTest :
    FunSpec({
        test("normal: create task definition") {
            application {
            }
        }
    })

// class TaskIntegrationTest:
//    FunSpec(),
//    KoinTest {
//    init {
//        val postgres = PostgreSQLContainer("postgres:17.6-alpine")
//
//        beforeSpec {
//            postgres.start()
//            startKoin {
//                modules(
//                    module {
//                        single {
//                            DBSettings(
//                                url = postgres.jdbcUrl,
//                                user = postgres.username,
//                                password = postgres.password,
//                                driver = postgres.driverClassName,
//                                maxPoolSize = 10,
//                            )
//                        } bind DBSettings::class
//                        singleOf(::provideDataSource) bind DataSource::class
//                        singleOf(::provideDSLContext) bind DSLContext::class
//                        singleOf(::TaskRepositoryImpl) bind TaskRepository::class
//                        singleOf(::CreateTaskDefinition)
//                        singleOf(::CreateTaskRequestHandler)
//                    },
//                )
//            }
//            Flyway
//                .configure()
//                .dataSource(postgres.jdbcUrl, postgres.username, postgres.password)
//                .locations("classpath:db/migration")
//                .load()
//                .migrate()
//        }
//
//        afterSpec {
//            stopKoin()
//            postgres.stop()
//        }
//
//        test("normal: create task") {
//            // arrange
//
//            // act
//            val response = client.get("/")
//
//            // assert
//        }
// }
