package com.example.myshop.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.myshop.data.Subcategory
import com.example.myshop.fragments.SubCategoryProductFragment

class SubcategoryPagerAdapter(
    private val subcategoryList: List<Subcategory>,
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle
) : FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int = subcategoryList.size

    override fun createFragment(position: Int): Fragment {
        val subcategory = subcategoryList[position]
        return SubCategoryProductFragment.newInstance(subcategory.subcategoryId)
    }
}
