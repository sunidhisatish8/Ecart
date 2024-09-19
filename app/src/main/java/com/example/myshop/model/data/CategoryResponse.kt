package com.example.myshop.model.data

data class CategoryResponse(
    val categories: List<Category>,
    val message: String,
    val status: Int
)