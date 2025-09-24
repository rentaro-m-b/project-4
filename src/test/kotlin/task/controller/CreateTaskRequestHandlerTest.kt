package task.controller

import com.example.task.controller.CreateTaskRequest
import com.example.task.controller.CreateTaskRequestHandler
import com.example.task.usecase.CreateTaskDefinition
import com.example.task.usecase.CreateTaskDefinitionCommand
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.inject
import java.math.BigDecimal
import java.util.UUID

class CreateTaskRequestHandlerTest :
    FunSpec(),
    KoinTest {
    init {
        val target by inject<CreateTaskRequestHandler>()
        val useCase = mockk<CreateTaskDefinition>()

        beforeSpec {
            startKoin {
                modules(
                    module {
                        single { useCase }
                        singleOf(::CreateTaskRequestHandler)
                    },
                )
            }
        }

        afterSpec {
            stopKoin()
        }

        test("normal: create task ticket definition") {
            // arrange
            every {
                useCase.execute(
                    CreateTaskDefinitionCommand(
                        description = "「テスト駆動開発」を読む",
                        expected = BigDecimal("5"),
                        unit = "page",
                        cyclePerDays = 1,
                    ),
                )
            } returns UUID.fromString("3ee1f358-690f-4ac7-8eec-8e3be49419df")

            // act
            val id =
                target.handle(
                    request =
                        CreateTaskRequest(
                            description = "「テスト駆動開発」を読む",
                            expected = "5",
                            unit = "page",
                            cyclePerDays = 1,
                        ),
                )

            // assert
            id shouldBe UUID.fromString("3ee1f358-690f-4ac7-8eec-8e3be49419df")
        }
    }
}
