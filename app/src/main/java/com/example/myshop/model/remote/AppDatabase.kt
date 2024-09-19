package com.example.myshop.model.remote

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.myshop.model.data.CartItems

@Database(entities = [CartItems::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun cartDao(): CartDao

    companion object {
        private lateinit var INSTANCE: AppDatabase
        fun getInstance(context: Context): AppDatabase {
            if (!this::INSTANCE.isInitialized) {
                INSTANCE = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "CartDB"
                ).allowMainThreadQueries().build()
            }
            return INSTANCE
        }
    }

}