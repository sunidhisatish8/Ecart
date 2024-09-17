package com.example.myshop.model.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.myshop.model.local.CartDetails

interface IRepository {
    fun getCartDetails(): LiveData<List<CartDetails>>
    fun updateCartDetails()
    val isProcessing: MutableLiveData<Boolean>
}