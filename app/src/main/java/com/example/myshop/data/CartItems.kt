package com.example.myshop.data

data class CartItems(
    val id: Int,
    val name: String,
    val price: Double,
    var quantity: Int
)