package com.example.myshop.model.data

data class SubCategoriesResponse(
    val message: String,
    val status: Int,
    val subcategories: List<Subcategory>
)