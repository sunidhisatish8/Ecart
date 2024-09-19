package com.example.myshop.model.data

data class ExistingAddressResponse(
    val addresses: List<Addresse>,
    val message: String,
    val status: Int
)