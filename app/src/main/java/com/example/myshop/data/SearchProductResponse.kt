package com.example.myshop.data

data class SearchProductResponse(
    val message: String,
    val products: List<Product>,
    val status: Int
)