package task.controller

import com.example.task.controller.CreateTaskRequest
import com.example.task.controller.CreateTaskRequestHandler
import com.example.task.usecase.CreateTaskTicketDefinitionCommand
import com.example.task.usecase.CreateTaskTicketDefinitionUseCase
import io.kotest.core.spec.style.FunSpec
import io.kotest.koin.KoinExtension
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import org.koin.core.module.Module
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
        val useCase = mockk<CreateTaskTicketDefinitionUseCase>()

        val testModule =
            module {
                single { useCase }
                singleOf(::CreateTaskRequestHandler)
            }
        extensions(KoinExtension(testModule))

        val target by inject<CreateTaskRequestHandler>()

        test("normal: create task ticket definition") {
            // arrange
            every {
                useCase.execute(
                    CreateTaskTicketDefinitionCommand(
                        description = "「テスト駆動開発」を読む",
                        expected = BigDecimal("5"),
                        unit = "page",
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
                        ),
                )

            // assert
            id shouldBe UUID.fromString("3ee1f358-690f-4ac7-8eec-8e3be49419df")
        }
    }
}
