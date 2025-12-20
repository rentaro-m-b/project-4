package com.example.controller.common

import kotlinx.serialization.Serializable

@Serializable
data class ErrorResponse(
    val type: String,
    val title: String,
    val detail: String,
    val instance: String,
)
