package it

import com.example.module
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.HttpStatusCode.Companion.OK
import io.ktor.server.config.ApplicationConfig
import io.ktor.server.testing.testApplication
import kotlin.test.Test
import kotlin.test.assertEquals

class HealthsApiTest {
    @Test
    fun getHealth() =
        testApplication {
            environment {
                config = ApplicationConfig("application.yaml")
            }
            application {
                module()
            }
            val response = client.get("/health")
            assertEquals(OK, response.status)
            assertEquals("Hello, World!", response.body())
        }
}
