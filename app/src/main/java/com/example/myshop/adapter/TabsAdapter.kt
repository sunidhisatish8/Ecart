package com.example.myshop.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.myshop.FragmentNames
import com.example.myshop.fragments.AddressFragment
import com.example.myshop.fragments.CartFragment
import com.example.myshop.fragments.PaymentFragment
import com.example.myshop.fragments.SummaryFragment

class TabsAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = FragmentNames.entries.size

    override fun createFragment(position: Int):
            Fragment = FragmentNames.entries[position].fragment
}