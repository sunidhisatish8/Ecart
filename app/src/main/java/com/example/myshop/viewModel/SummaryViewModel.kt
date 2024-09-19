package com.example.myshop.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myshop.model.data.OrderDetailsResponse
import com.example.myshop.model.data.PlaceOrderRequest
import com.example.myshop.model.data.PlaceOrderResponse
import com.example.myshop.model.remote.ApiClient
import com.example.myshop.model.remote.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SummaryViewModel : ViewModel() {
    private val _placeOrder = MutableLiveData<PlaceOrderResponse>()
    var placeOrder: LiveData<PlaceOrderResponse> = _placeOrder
    private val _summaryOrder = MutableLiveData<OrderDetailsResponse>()
    var summaryOrder: LiveData<OrderDetailsResponse> = _summaryOrder
    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    fun getOrderDetails(placeOrderRequest: PlaceOrderRequest) {
        val apiService = ApiClient.myShopRetrofit.create(ApiService::class.java)
        apiService.placeOrder(placeOrderRequest).enqueue(
            object : Callback<PlaceOrderResponse> {
                override fun onResponse(
                    call: Call<PlaceOrderResponse>,
                    response: Response<PlaceOrderResponse>
                ) {
                    Log.i("errorOrder", response.code().toString())
                    if (response.isSuccessful) {
                        _placeOrder.value = response.body()
                    }
                }

                override fun onFailure(call: Call<PlaceOrderResponse>, t: Throwable) {
                    _error.value = t.message
                    t.printStackTrace()
                }
            }
        )
    }

    fun getOrderSummary(orderId: String) {
        val apiService = ApiClient.myShopRetrofit.create(ApiService::class.java)
        apiService.getOrderDetails(orderId).enqueue(
            object : Callback<OrderDetailsResponse> {
                override fun onResponse(
                    call: Call<OrderDetailsResponse>,
                    response: Response<OrderDetailsResponse>
                ) {
                    Log.i("errorOrder", response.code().toString())
                    if (response.isSuccessful) {
                        _summaryOrder.value = response.body()
                    }
                }

                override fun onFailure(call: Call<OrderDetailsResponse>, t: Throwable) {
                    _error.value = t.message
                    t.printStackTrace()
                }
            }
        )
    }
}