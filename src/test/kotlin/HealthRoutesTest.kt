import com.example.module
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.server.config.ApplicationConfig
import io.ktor.server.testing.*
import kotlin.test.Test
import kotlin.test.assertEquals

class HealthRoutesTest {
    @Test
    fun getHealth() = testApplication {
        environment {
            config = ApplicationConfig("application.yaml")
        }
        application {
            module()
        }
        val response = client.get("/health")
        assertEquals(HttpStatusCode.OK, response.status)
        assertEquals("Hello, World!", response.body())
    }
}
