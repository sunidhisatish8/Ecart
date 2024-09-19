package com.example.myshop.model.data

import com.google.gson.annotations.SerializedName

data class PlaceOrderResponse(
    val message: String,
    @SerializedName("order_id")
    val orderId: Int,
    val status: Int
)