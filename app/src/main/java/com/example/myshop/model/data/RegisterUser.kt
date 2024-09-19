package com.example.myshop.model.data

import com.google.gson.annotations.SerializedName

data class RegisterUser(
    @SerializedName("full_name")
    val fullName: String,

    @SerializedName("mobile_no")
    val mobileNumber: String,

    @SerializedName("email_id")
    val emailId: String,

    @SerializedName("password")
    val password: String
)