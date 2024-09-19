package com.example.myshop.model.remote

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update
import com.example.myshop.model.data.CartItems

@Dao
interface CartDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun saveCartDetails(cartItems: List<CartItems>): List<Long>

    @Update
    fun updateCartDetails(cartItems: List<CartItems>): Int
}