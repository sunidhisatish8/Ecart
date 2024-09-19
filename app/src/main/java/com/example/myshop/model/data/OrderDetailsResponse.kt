package com.example.myshop.model.data

data class OrderDetailsResponse(
    val message: String,
    val order: Order,
    val status: Int
)