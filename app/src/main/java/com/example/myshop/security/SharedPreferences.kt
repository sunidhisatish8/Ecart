package com.example.myshop.security

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey

object SharedPreferences {
    private const val PREF_FILE_NAME = "pref_of_app"
    const val KEY_EMAIL = "email"
    const val KEY_USER_ID = "userId"
    const val KEY_PASSWORD = "password"
    const val KEY_IS_LOGIN = "isLogin"
    const val KEY_IS_NEXT = "isNext"
    const val KEY_IS_ONBOARD = "isOnboard"

    private lateinit var sharedPreferences: SharedPreferences
    fun init(context: Context) {
        var masterKey = MasterKey.Builder(context)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()

        sharedPreferences = EncryptedSharedPreferences.create(
            context,
            PREF_FILE_NAME,
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    fun saveString(key: String, value: String) {
        sharedPreferences.edit().putString(key, value).apply()
    }

    fun getString(key: String, value: String): String {
        return sharedPreferences.getString(key, value) ?: value
    }

    fun saveBoolean(key: String, value: Boolean) {
        sharedPreferences.edit().putBoolean(key, value).apply()
    }

    fun getBoolean(key: String, value: Boolean): Boolean {
        return sharedPreferences.getBoolean(key, value) ?: value
    }
}