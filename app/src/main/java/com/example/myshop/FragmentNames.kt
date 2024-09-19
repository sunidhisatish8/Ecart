package com.example.myshop

import androidx.fragment.app.Fragment
import com.example.myshop.fragments.AddressFragment
import com.example.myshop.fragments.CartFragment
import com.example.myshop.fragments.PaymentFragment
import com.example.myshop.fragments.SummaryFragment

enum class FragmentNames(val fragmentNames : String, val fragment : Fragment) {
    CART_FRAGMENT("Cart Details", CartFragment()),
    ADDRESS_FRAGMENT("Address", AddressFragment()),
    PAYMENT_FRAGMENT("Payment", PaymentFragment()),
    ORDER_SUMMARY("Order Summary", SummaryFragment())
}