package com.example.myshop.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.example.myshop.adapter.CategoryAdapter
import com.example.myshop.R
import com.example.myshop.data.Category
import com.example.myshop.data.CategoryResponse
import com.example.myshop.databinding.FragmentCategoryBinding
import com.example.myshop.remote.ApiClient
import com.example.myshop.remote.ApiService
import com.example.myshop.showMessage
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CategoryFragment : Fragment() {
    private lateinit var binding: FragmentCategoryBinding
    private lateinit var adapter: CategoryAdapter
    private lateinit var apiService: ApiService
    private val categoryList = ArrayList<Category>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCategoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        viewCategories()
    }

    private fun setupRecyclerView() {
        binding.recyclerView.layoutManager =  GridLayoutManager(requireContext(), 2)
        adapter = CategoryAdapter(categoryList) { category -> onCategoryClick(category) }
        binding.recyclerView.adapter = adapter
    }

    private fun viewCategories() {
        apiService = ApiClient.myShopRetrofit.create(ApiService::class.java)
        val call: Call<CategoryResponse> = apiService.getCategoryDetails()
        call.enqueue(object : Callback<CategoryResponse> {
            override fun onResponse(
                call: Call<CategoryResponse>,
                response: Response<CategoryResponse>
            ) {
                if (!response.isSuccessful) {
                    showMessage("Failed", "Error is ${response.code()}")
                }

                val result: CategoryResponse? = response.body()
                if (result != null && result.categories.isNotEmpty()) {
                    categoryList.clear()
                    categoryList.addAll(result.categories)
                    adapter.notifyDataSetChanged()
                }
            }

            override fun onFailure(call: Call<CategoryResponse>, t: Throwable) {
                t.printStackTrace()
                showMessage("Failed", "$t")
            }
        })
    }

    private fun onCategoryClick(category: Category) {
        val mainFragment = MainFragment().apply {
            arguments = Bundle().apply {
                putString("categoryId", category.categoryId)
            }
        }
        activity?.supportFragmentManager?.beginTransaction()
            ?.replace(R.id.fragment_container, mainFragment)
            ?.addToBackStack(null)
            ?.commit()
    }
}
