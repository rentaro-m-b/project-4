package com.example.controller

import com.example.DBSettings
import com.example.common.CommonDIContainer
import com.example.task.TaskDIContainer
import com.example.task.controller.CreateTaskRequest
import com.example.task.controller.CreateTaskRequestHandler
import io.kotest.core.spec.style.FunSpec
import io.kotest.koin.KoinExtension
import io.kotest.matchers.shouldBe
import io.ktor.server.config.yaml.YamlConfigLoader
import org.koin.test.KoinTest
import org.koin.test.inject

class CreateTaskRequestHandlerTest :
    FunSpec(),
    KoinTest {
    init {
        val config =
            YamlConfigLoader()
                .load("application.yaml")
                ?.config("db")
                ?: error("application.yaml not found")

        val testModule =
            listOf(
                TaskDIContainer.defineModule(),
                CommonDIContainer.defineModule(DBSettings.of(config)),
            )

        extensions(KoinExtension(modules = testModule))

        val target by inject<CreateTaskRequestHandler>()

        test("normal: create task ticket definition") {
            val id =
                target.handle(
                    request =
                        CreateTaskRequest(
                            description = "「テスト駆動開発」を読む",
                            expected = "5",
                            unit = "page",
                        ),
                )
            id.toString().isNotBlank() shouldBe true
        }
    }
}
