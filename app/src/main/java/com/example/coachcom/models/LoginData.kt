package com.example.coachcom.models


data class LoginData(
    val insertedUsername: String,
    val insertedPassword: String
)
data class ResponseData(
    val success: String,
    val message: String,
    val data: List<Any>
)