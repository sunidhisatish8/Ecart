package com.example.myshop.model.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface CartDao {
    @Insert()
    fun saveCartDetails(cartDetails: CartDetails)

    @Query("SELECT * FROM Cart")
    fun getCartDetails(): LiveData<List<CartDetails>>

}