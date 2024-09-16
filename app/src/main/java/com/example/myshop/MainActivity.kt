package com.example.myshop

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.myshop.data.LoginRequest
import com.example.myshop.data.LoginResponse
import com.example.myshop.databinding.ActivityMainBinding
import com.example.myshop.remote.ApiClient
import com.example.myshop.remote.ApiService
import com.example.myshop.security.SharedPreferences
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val apiService: ApiService = ApiClient.myShopRetrofit.create(ApiService::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val isLogin = SharedPreferences.getBoolean(SharedPreferences.KEY_IS_LOGIN, false)
        if (isLogin) {
            navigateToDashboard()
        } else {
            binding.loginButton.setOnClickListener {
                loginWithUserDetails()
            }

            binding.btnSignUp.setOnClickListener {
                val intent = Intent(this, RegisterActivity::class.java)
                startActivity(intent)
            }
        }
    }

    private fun loginWithUserDetails() {
        val emailID = binding.emailEditText.text.toString()
        val password = binding.passwordEditText.text.toString()
        val login = LoginRequest(emailID, password)
        SharedPreferences.saveString(SharedPreferences.KEY_EMAIL, emailID)
        SharedPreferences.saveString(SharedPreferences.KEY_PASSWORD, password)

        val call: Call<LoginResponse> = apiService.addLoginDetails(login)
        call.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (!response.isSuccessful) {
                    showMessage(
                        "Failed",
                        "Failed to login with the user details : ${response.code()}"
                    )
                }
                val result: LoginResponse? = response.body()
                if (result == null) {
                    showMessage("Failed", "Empty message")
                }
                if (result?.status == 0) {
                    showMessage("Successful", "Logged in Successfully")
                    SharedPreferences.saveBoolean(SharedPreferences.KEY_IS_LOGIN, true)
                    navigateToDashboard()
                } else {
                    result?.message?.let { showMessage("Failed", it) }
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                t.printStackTrace()
                showMessage("Failed", "$t")
            }

        })
    }

    fun navigateToDashboard() {
        val intent = Intent(this@MainActivity, DashboardActivity::class.java)
        startActivity(intent)
    }

}