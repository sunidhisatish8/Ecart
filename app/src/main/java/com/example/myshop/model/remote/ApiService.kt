package com.example.myshop.model.remote

import com.example.myshop.model.data.AddressResponse
import com.example.myshop.model.data.CategoryResponse
import com.example.myshop.model.data.ExistingAddressResponse
import com.example.myshop.model.data.LoginRequest
import com.example.myshop.model.data.LoginResponse
import com.example.myshop.model.data.OrderDetailsResponse
import com.example.myshop.model.data.PlaceOrderRequest
import com.example.myshop.model.data.PlaceOrderResponse
import com.example.myshop.model.data.RegisterResponse
import com.example.myshop.model.data.RegisterUser
import com.example.myshop.model.data.SearchProductResponse
import com.example.myshop.model.data.SubCategoriesResponse
import com.example.myshop.model.data.SubCategoryProductResponse
import com.example.myshop.model.data.UserAddress
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

    @POST("User/address")
    fun addUserAddress(
        @Body userAddress: UserAddress
    ) : Call<AddressResponse>

    @GET("User/addresses/{user_id}")
    fun getUserAddress(
        @Path("user_id") userId : String
    ) : Call<ExistingAddressResponse>

    @POST("Order")
    fun placeOrder(
        @Body placeOrder : PlaceOrderRequest
    ) : Call<PlaceOrderResponse>

    @GET("Order")
    fun getOrderDetails(
        @Query("order_id") orderId : String
    ) : Call<OrderDetailsResponse>
}