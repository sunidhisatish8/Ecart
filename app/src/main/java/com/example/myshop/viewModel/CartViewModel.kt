package com.example.myshop.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myshop.data.CartItems
import com.example.myshop.data.Product

class CartViewModel : ViewModel() {
    private var _cartData = MutableLiveData<MutableList<CartItems>>()
    val cartData: LiveData<MutableList<CartItems>> = _cartData

    fun addToCart(product: Product) {
        val currentCartItems = _cartData.value ?: mutableListOf()
        val existingItem = currentCartItems.find { it.name == product.productName }

        if (existingItem != null) {
            existingItem.quantity += 1
        } else {
            currentCartItems.add(
                CartItems(
                    product.productId.toInt(),
                    product.productName,
                    product.price.toDouble(),
                    1
                )
            )
        }

        _cartData.value = currentCartItems
    }

    fun increaseQuantity(product: Product) {
        val currentCartItems = _cartData.value ?: mutableListOf()
        val existingItem = currentCartItems.find { it.name == product.productName }

        existingItem?.let {
            it.quantity += 1
            _cartData.value = currentCartItems
        }
    }

    fun decreaseQuantity(product: Product) {
        val currentCartItems = _cartData.value ?: mutableListOf()
        val existingItem = currentCartItems.find { it.name == product.productName }

        existingItem?.let {
            if (it.quantity > 1) {
                it.quantity -= 1
                _cartData.value = currentCartItems
            } else {
                currentCartItems.remove(it)
                _cartData.value = currentCartItems
            }
        }
    }
}