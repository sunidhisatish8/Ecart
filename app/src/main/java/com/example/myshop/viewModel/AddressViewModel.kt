package com.example.myshop.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myshop.model.data.AddressResponse
import com.example.myshop.model.data.DeliveryAddress
import com.example.myshop.model.data.ExistingAddressResponse
import com.example.myshop.model.data.SubCategoryProductResponse
import com.example.myshop.model.data.UserAddress
import com.example.myshop.model.remote.ApiClient
import com.example.myshop.model.remote.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddressViewModel : ViewModel() {
    private lateinit var apiService: ApiService
    private val _addressResponse = MutableLiveData<AddressResponse>()
    val addressResponse: LiveData<AddressResponse> = _addressResponse
    private val _selectedAddress = MutableLiveData<DeliveryAddress>()
    val selectedAddress: LiveData<DeliveryAddress> = _selectedAddress
    private val _existingAddressResponse = MutableLiveData<ExistingAddressResponse>()
    val existingAddressResponse: LiveData<ExistingAddressResponse> = _existingAddressResponse
    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    fun addProductDetails(userAddress: UserAddress) {
        apiService = ApiClient.myShopRetrofit.create(ApiService::class.java)
        apiService.addUserAddress(userAddress).enqueue(
            object : Callback<AddressResponse> {
                override fun onResponse(
                    call: Call<AddressResponse>,
                    response: Response<AddressResponse>
                ) {
                    if (response.isSuccessful) {
                        _addressResponse.value = response.body()
                    }
                }

                override fun onFailure(call: Call<AddressResponse>, t: Throwable) {
                    t.printStackTrace()
                    _error.value = t.message
                }
            }
        )
    }

    fun getAddressDetails(userId: String) {
        apiService = ApiClient.myShopRetrofit.create(ApiService::class.java)
        apiService.getUserAddress(userId).enqueue(
            object : Callback<ExistingAddressResponse> {
                override fun onResponse(
                    call: Call<ExistingAddressResponse>,
                    response: Response<ExistingAddressResponse>
                ) {
                    if (response.isSuccessful) {
                        _existingAddressResponse.value = response.body()
                    }
                }

                override fun onFailure(call: Call<ExistingAddressResponse>, t: Throwable) {
                    t.printStackTrace()
                    _error.value = t.message
                }
            }
        )
    }

    fun setSelectedAddress(userAddress: DeliveryAddress) {
        _selectedAddress.value = userAddress
    }
}