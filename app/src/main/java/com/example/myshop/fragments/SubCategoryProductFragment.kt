package com.example.myshop.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myshop.Constants.IS_SEARCH_CLICKED
import com.example.myshop.Constants.SEARCH_TEXT
import com.example.myshop.Constants.SUBCATEGORY_ID
import com.example.myshop.R
import com.example.myshop.SubCategoryIdProvider
import com.example.myshop.adapter.ProductPagerAdapter
import com.example.myshop.adapter.ProductRecyclerAdapter
import com.example.myshop.model.data.Product
import com.example.myshop.model.data.SubCategoryProductResponse
import com.example.myshop.databinding.FragmentSubCategoryProductBinding
import com.example.myshop.model.remote.ApiClient
import com.example.myshop.model.remote.ApiService
import com.example.myshop.viewModel.CartViewModel
import com.example.myshop.viewModel.ProductViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SubCategoryProductFragment : Fragment() {
    private lateinit var binding: FragmentSubCategoryProductBinding
    private lateinit var productViewModel: ProductViewModel
    private val productList = ArrayList<Product>()
    private lateinit var cartViewModel: CartViewModel
    private lateinit var adapter: ProductRecyclerAdapter

    companion object {
        fun newInstance(subCategoryId: String): SubCategoryProductFragment {
            val fragment = SubCategoryProductFragment()
            val args = Bundle()
            args.putString(SUBCATEGORY_ID, subCategoryId)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSubCategoryProductBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        cartViewModel = ViewModelProvider(requireActivity())[CartViewModel::class.java]
        setUpRecyclerView()
        if (IS_SEARCH_CLICKED) {
            getSearchProducts()
        } else {
            getSubCategoryProductFragment()
        }

        binding.viewCartButton.setOnClickListener {
            navigateToCartFragment()
        }
    }

    private fun getSearchProducts() {
        productViewModel = ViewModelProvider(this)[ProductViewModel::class.java]
        val searchText = arguments?.getString(SEARCH_TEXT)
        if (searchText != null) {
            productViewModel.searchProducts(searchText)
        }
        productViewModel.searchProduct.observe(viewLifecycleOwner) { response ->
            response?.let {
                productList.clear()
                productList.addAll(it.products)
                adapter.notifyDataSetChanged()
            }
        }
    }

    private fun getSubCategoryProductFragment() {
        productViewModel = ViewModelProvider(this)[ProductViewModel::class.java]
        val subCategoryId = arguments?.getString(SUBCATEGORY_ID)
        productViewModel.fetchProductDetails(subCategoryId)
        productViewModel.productResponse.observe(viewLifecycleOwner) { response ->
            response?.let {
                productList.clear()
                productList.addAll(it.products)
                adapter.notifyDataSetChanged()
            }
        }
    }

    private fun setUpRecyclerView() {
        binding.productRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter = ProductRecyclerAdapter(productList, cartViewModel)
        binding.productRecyclerView.adapter = adapter
    }

    private fun navigateToCartFragment() {
        activity?.supportFragmentManager?.beginTransaction()
            ?.replace(R.id.fragment_container, CheckoutFragment())
            ?.addToBackStack(null)
            ?.commit()
    }
}