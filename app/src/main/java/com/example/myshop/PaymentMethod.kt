package com.example.myshop

enum class PaymentMethod(val paymentMethod: String) {
    CASH_ON_DELIVERY("COD"),
    CREDIT_CARD("Credit Card"),
    DEBIT_CARD("Debit Card"),
    PAYPAL("payPal")
}