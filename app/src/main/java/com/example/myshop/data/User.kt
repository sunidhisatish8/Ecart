package com.example.myshop.data

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("full_name")
    val fullName: String,

    @SerializedName("mobile_no")
    val mobileNumber: String,

    @SerializedName("email_id")
    val emailId: String,

    @SerializedName("user_id")
    val userId: String
)