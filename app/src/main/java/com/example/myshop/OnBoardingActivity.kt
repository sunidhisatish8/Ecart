package com.example.myshop

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.myshop.databinding.ActivityMainBinding
import com.example.myshop.databinding.ActivityOnBoardingBinding
import com.example.myshop.fragments.OnBoardingOneFragment
import com.example.myshop.security.SharedPreferences

class OnBoardingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOnBoardingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnBoardingBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        val isLogin = SharedPreferences.getBoolean(SharedPreferences.KEY_IS_ONBOARD, false)
        if (isLogin) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        } else {
            supportFragmentManager.beginTransaction()
                .replace(R.id.onboard_fragment_container, OnBoardingOneFragment())
                .addToBackStack(null)
                .commit()
        }
    }
}