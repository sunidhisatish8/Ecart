package com.example.myshop.security

import android.app.Application

class App: Application() {
    override fun onCreate() {
        super.onCreate()
        SharedPreferences.init(this)
    }
}