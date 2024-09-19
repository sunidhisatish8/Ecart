package com.example.myshop.model.data

data class LoginResponse(
    val message: String,
    val status: Int,
    val user: User
)