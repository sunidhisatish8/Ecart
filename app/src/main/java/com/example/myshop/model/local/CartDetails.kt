package com.example.myshop.model.local

import androidx.room.Entity

@Entity(tableName = "Cart")
data class CartDetails(
    val id: Int,
    val name: String,
    val price: Double,
    var quantity: Int
)