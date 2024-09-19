package com.example.myshop.model.data

data class Order(
    val address: String,
    val address_title: String,
    val bill_amount: String,
    val items: List<ItemX>,
    val order_date: String,
    val order_id: String,
    val order_status: String,
    val payment_method: String
)