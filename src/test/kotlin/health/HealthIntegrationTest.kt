package health

import com.example.module
import com.example.task.controller.CreateTaskRequest
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode.Companion.OK
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.config.yaml.YamlConfigLoader
import io.ktor.server.testing.testApplication
import io.netty.handler.codec.http.HttpHeaderNames.LOCATION
import org.koin.core.context.stopKoin

class HealthIntegrationTest :
    FunSpec({
        afterSpec {
            stopKoin()
        }

        test("normal: health check") {
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
                val response = client.get("/health")

                // assert
                response.status shouldBe OK
            }
        }
    })
