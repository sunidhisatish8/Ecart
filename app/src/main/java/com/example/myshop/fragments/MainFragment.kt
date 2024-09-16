package com.example.myshop.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.myshop.adapter.SubcategoryPagerAdapter
import com.example.myshop.data.Subcategory
import com.example.myshop.data.SubCategoriesResponse
import com.example.myshop.databinding.FragmentMainBinding
import com.example.myshop.remote.ApiClient
import com.example.myshop.remote.ApiService
import com.example.myshop.showMessage
import com.google.android.material.tabs.TabLayoutMediator
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainFragment : Fragment() {
    private lateinit var binding: FragmentMainBinding
    private val subcategoryList = ArrayList<Subcategory>()
    private lateinit var apiService: ApiService

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val categoryId = arguments?.getString("categoryId")

        apiService = ApiClient.myShopRetrofit.create(ApiService::class.java)
        val call: Call<SubCategoriesResponse>? = categoryId?.let {
            apiService.getSubCategoryDetails(
                it
            )
        }
        call?.enqueue(object : Callback<SubCategoriesResponse> {
            override fun onResponse(
                call: Call<SubCategoriesResponse>,
                response: Response<SubCategoriesResponse>
            ) {
                if (!response.isSuccessful) {
                    showMessage("Failed", "Error is ${response.code()}")
                }

                val result: SubCategoriesResponse? = response.body()
                if (result != null && result.subcategories.isNotEmpty()) {
                    subcategoryList.addAll(result.subcategories)
                    val adapter =
                        SubcategoryPagerAdapter(subcategoryList, childFragmentManager, lifecycle)
                    binding.viewPager.adapter = adapter

                    TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
                        tab.text = subcategoryList[position].subcategory_name
                    }.attach()
                }
            }

            override fun onFailure(call: Call<SubCategoriesResponse>, t: Throwable) {
                t.printStackTrace()
                showMessage("Failed", "$t")
            }
        })
    }
}
