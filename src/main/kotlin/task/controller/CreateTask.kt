package com.example.task.controller

import java.math.BigDecimal

// TODO: ロケーションヘッダーを返す方法を探す
fun createTask(
    request: CreateTaskRequest,
) {

}

data class CreateTaskRequest(
    val description: String,
    val expected: BigDecimal,
    val unit: String,
)
