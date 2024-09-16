package com.example.myshop.data

data class SubCategoryProductResponse(
    val message: String,
    val products: List<Product>,
    val status: Int
)