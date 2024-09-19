package com.example.myshop.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.myshop.model.data.CartItems
import com.example.myshop.model.data.Product
import com.example.myshop.model.remote.AppDatabase

class CartViewModel(application: Application) : AndroidViewModel(application)  {
    private val cartDao = AppDatabase.getInstance(application).cartDao()
    private var _cartData = MutableLiveData<MutableList<CartItems>>()
    val cartData: LiveData<MutableList<CartItems>> = _cartData
    private var _orderTotal = MutableLiveData<Int>()
    val orderTotal: LiveData<Int> = _orderTotal


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
        cartDao.saveCartDetails(currentCartItems)
    }

    fun increaseQuantity(product: Product) {
        val currentCartItems = _cartData.value ?: mutableListOf()
        val existingItem = currentCartItems.find { it.name == product.productName }

        existingItem?.let {
            it.quantity += 1
            _cartData.value = currentCartItems
        }
        cartDao.updateCartDetails(currentCartItems)
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
        cartDao.updateCartDetails(currentCartItems)
    }

    fun setTotalPrice() {
        val currentCartItems = _cartData.value ?: mutableListOf()
        _orderTotal.value = currentCartItems.sumOf { it.price * it.quantity }.toInt()
    }
}