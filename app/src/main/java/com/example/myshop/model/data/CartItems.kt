package com.example.myshop.model.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Cart")
data class CartItems(
    @PrimaryKey
    val id: Int,
    val name: String,
    val price: Double,
    var quantity: Int
)