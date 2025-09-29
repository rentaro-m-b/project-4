package task

import com.example.configureRouting
import com.example.module
import com.example.task.controller.CreateTaskRequest
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode.Companion.Created
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.config.yaml.YamlConfigLoader
import io.ktor.server.netty.EngineMain
import io.ktor.server.testing.testApplication
import org.flywaydb.core.Flyway
import org.koin.core.context.stopKoin

class TaskIntegrationTest :
    FunSpec({
//        beforeSpec {
//            val config = YamlConfigLoader().load("application.yaml")!!
//            val url = config.property("db.url").getString()
//            val user = config.property("db.user").getString()
//            val password = config.property("db.password").getString()
//            Flyway
//                .configure()
//                .dataSource(url, user, password)
//                .locations("classpath:db/migration")
//                .schemas("main")
//                .defaultSchema("main")
//                .load()
//                .migrate()
//        }

        afterSpec {
            stopKoin()
        }

        test("normal: create task definition") {
            testApplication {
                // arrange
                environment {
                    config = YamlConfigLoader().load("application.yaml")!!
                }
                application {
                    module()
                }

                client =
                    createClient {
                        install(ContentNegotiation) {
                            json()
                        }
                    }

                // act
                val response =
                    client.post("/tasks") {
                        contentType(ContentType.Application.Json)
                        setBody(
                            CreateTaskRequest(
                                description = "「テスト駆動開発」を読む",
                                expected = "5",
                                unit = "page",
                                cyclePerDays = 1,
                            ),
                        )
                    }

                // assert
//                val dsl = GlobalContext.get().get<DSLContext>()
//                val expected =
//                    dsl
//                        .selectFrom(TASK_DEFINITIONS)
//                        .orderBy(TASK_DEFINITIONS.CREATED_AT)
//                        .fetchOne() ?: error("row not found")
                response.status shouldBe Created
//                response.headers[LOCATION.toString()] shouldBe expected.id
//                UUID.fromString(response.headers[LOCATION.toString()]).version() shouldBe 4
            }
        }
    })
