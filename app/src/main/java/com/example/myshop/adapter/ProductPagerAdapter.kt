package com.example.myshop.adapter

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.myshop.data.Product
import com.example.myshop.fragments.SubCategoryProductFragment

class ProductPagerAdapter(
    private val productList: List<Product>,
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle
) : FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun getItemCount(): Int = productList.size

    override fun createFragment(position: Int): Fragment {
        val productFragment = SubCategoryProductFragment()
        val product = productList[position]
        val bundle = Bundle()
        bundle.apply {
            putString("productId", product.productId)
        }
        productFragment.arguments = bundle
        return productFragment
    }
}