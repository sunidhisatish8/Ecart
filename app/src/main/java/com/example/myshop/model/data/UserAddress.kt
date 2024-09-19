package com.example.myshop.model.data

import com.google.gson.annotations.SerializedName

data class UserAddress(
    val address: String,
    val title: String,
    @SerializedName("user_id")
    val userId: Int
)