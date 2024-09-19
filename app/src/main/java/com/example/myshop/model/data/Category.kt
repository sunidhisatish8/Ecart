package com.example.myshop.model.data

import com.google.gson.annotations.SerializedName

data class Category(
    @SerializedName("category_id")
    val categoryId: String,

    @SerializedName("category_image_url")
    val categoryImageUrl: String,

    @SerializedName("category_name")
    val categoryName: String,

    @SerializedName("is_active")
    val isActive: String
)