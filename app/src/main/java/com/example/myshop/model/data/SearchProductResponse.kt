package com.example.myshop.model.data

data class SearchProductResponse(
    val message: String,
    val products: List<Product>,
    val status: Int
)