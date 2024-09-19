package com.example.myshop.model.data

import com.google.gson.annotations.SerializedName

data class Item(
    @SerializedName("product_id")
    val productId: Int,
    val quantity: Int,
    @SerializedName("unit_price")
    val unitPrice: Int
)