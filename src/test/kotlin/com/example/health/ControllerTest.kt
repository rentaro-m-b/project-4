package com.example.health

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class ControllerTest : FunSpec({
    test("my first test") {
        controller() shouldBe "hello"
    }

})
