package com.example.myshop.remote

import com.example.myshop.data.CategoryResponse
import com.example.myshop.data.LoginRequest
import com.example.myshop.data.LoginResponse
import com.example.myshop.data.RegisterResponse
import com.example.myshop.data.RegisterUser
import com.example.myshop.data.SearchProductResponse
import com.example.myshop.data.SubCategoriesResponse
import com.example.myshop.data.SubCategoryProductResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @Headers("content-type: application/json")
    @POST("User/register")
    fun addRegisterDetails(
        @Body registerUser: RegisterUser
    ): Call<RegisterResponse>

    @Headers("content-type: application/json")
    @POST("User/auth")
    fun addLoginDetails(
        @Body loginRequest: LoginRequest
    ): Call<LoginResponse>

    @GET("Category")
    fun getCategoryDetails(): Call<CategoryResponse>

    @GET("SubCategory")
    fun getSubCategoryDetails(
        @Query("category_id") categoryId: String
    ): Call<SubCategoriesResponse>

    @GET("SubCategory/products/{sub_category_id}")
    fun getSubCategoryProducts(
        @Path("sub_category_id") subCategoryId:String
    ) : Call<SubCategoryProductResponse>

    @GET("Product/search")
    fun getSearchedProducts(
        @Query("query") searchText:String
    ) : Call<SearchProductResponse>
}