package com.example.myshop.model.data

import com.google.gson.annotations.SerializedName

data class ItemX(
    val amount: String,
    val description: String,
    @SerializedName("product_id")
    val productId: String,
    @SerializedName("product_image_url")
    val productImageUrl: String,
    @SerializedName("product_name")
    val productName: String,
    val quantity: String,
    @SerializedName("unit_price")
    val unitPrice: String
)