package com.example.myshop

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.myshop.data.LoginRequest
import com.example.myshop.data.LoginResponse
import com.example.myshop.data.RegisterResponse
import com.example.myshop.data.RegisterUser
import com.example.myshop.databinding.ActivityMainBinding
import com.example.myshop.databinding.ActivityRegisterBinding
import com.example.myshop.remote.ApiClient
import com.example.myshop.remote.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterActivity : AppCompatActivity() {
    lateinit var binding: ActivityRegisterBinding
    val apiService: ApiService = ApiClient.myShopRetrofit.create(ApiService::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.signUpButton.setOnClickListener {
            registerWithUserDetails()
        }
    }

    private fun registerWithUserDetails() {
        val fullName = binding.fullNameEditText.text.toString()
        val mobileNumber = binding.mobileNoEt.text.toString()
        val emailID = binding.emailEditText.text.toString()
        val password = binding.passwordEditText.text.toString()
        val register = RegisterUser(fullName, mobileNumber, emailID, password)
        val call: Call<RegisterResponse> = apiService.addRegisterDetails(register)
        call.enqueue(object : Callback<RegisterResponse> {
            override fun onResponse(call: Call<RegisterResponse>, response: Response<RegisterResponse>) {
                if (!response.isSuccessful) {
                    showMessage("Failed", "Failed to login with the user details : ${response.code()}")
                }
                val result: RegisterResponse? = response.body()
                if (result == null) {
                    showMessage("Failed", "Empty message")
                }
                if (result?.status == 0) {
                    showMessage("Successful", "Registered Successfully")
                } else {
                    result?.message?.let { showMessage("Failed", it) }
                }
            }

            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                t.printStackTrace()
                showMessage("Failed", "$t")
            }

        })
    }
}