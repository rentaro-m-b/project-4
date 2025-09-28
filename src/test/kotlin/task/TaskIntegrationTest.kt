package task

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
import io.ktor.server.testing.testApplication
import io.netty.handler.codec.http.HttpHeaderNames.LOCATION
import org.koin.core.context.stopKoin
import java.util.UUID

class TaskIntegrationTest :
    FunSpec({
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
                response.status shouldBe Created
                UUID.fromString(response.headers[LOCATION.toString()]).version() shouldBe 4
            }
        }
    })
