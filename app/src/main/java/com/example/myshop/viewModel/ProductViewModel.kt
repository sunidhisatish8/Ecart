package com.example.myshop.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myshop.Constants.SUBCATEGORY_ID
import com.example.myshop.data.Product
import com.example.myshop.data.SearchProductResponse
import com.example.myshop.data.SubCategoryProductResponse
import com.example.myshop.remote.ApiClient
import com.example.myshop.remote.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductViewModel: ViewModel() {
    private lateinit var apiService: ApiService
    private val _productResponse = MutableLiveData<SubCategoryProductResponse>()
    val productResponse: LiveData<SubCategoryProductResponse> = _productResponse
    private val _searchProduct = MutableLiveData<SearchProductResponse>()
    val searchProduct : LiveData<SearchProductResponse> = _searchProduct
    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    fun fetchProductDetails(subCategoryId: String?) {
        apiService = ApiClient.myShopRetrofit.create(ApiService::class.java)
        val call: Call<SubCategoryProductResponse>? = subCategoryId?.let {
            apiService.getSubCategoryProducts(it)
        }
        call?.enqueue(object : Callback<SubCategoryProductResponse> {
            override fun onResponse(
                call: Call<SubCategoryProductResponse>,
                response: Response<SubCategoryProductResponse>
            ) {
                if (response.isSuccessful) {
                    _productResponse.value = response.body()
                }
            }

            override fun onFailure(call: Call<SubCategoryProductResponse>, t: Throwable) {
                t.printStackTrace()
                _error.value = t.message
            }
        })
    }

    fun searchProducts(searchText:String){
        apiService = ApiClient.myShopRetrofit.create(ApiService::class.java)
        val call: Call<SearchProductResponse>? = searchText?.let {
            apiService.getSearchedProducts(it)
        }
        call?.enqueue(object : Callback<SearchProductResponse> {
            override fun onResponse(
                call: Call<SearchProductResponse>,
                response: Response<SearchProductResponse>
            ) {
                if (response.isSuccessful) {
                    _searchProduct.value = response.body()
                }
            }

            override fun onFailure(call: Call<SearchProductResponse>, t: Throwable) {
                t.printStackTrace()
                _error.value = t.message
            }
        })
    }
}