package com.example.myshop.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myshop.R
import com.example.myshop.adapter.CartItemAdapter
import com.example.myshop.databinding.FragmentCartBinding
import com.example.myshop.viewModel.CartViewModel

class CartFragment : Fragment() {
    private lateinit var binding: FragmentCartBinding
    private lateinit var cartViewModel: CartViewModel
    private lateinit var cartAdapter: CartItemAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        cartViewModel = ViewModelProvider(requireActivity())[CartViewModel::class.java]
        setUpRecyclerView()
        cartViewModel.cartData.observe(viewLifecycleOwner) { cartItems ->
            cartAdapter.updateCartItems(cartItems)
        }
    }

    private fun setUpRecyclerView() {
        binding.cartRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        cartAdapter = CartItemAdapter()
        binding.cartRecyclerView.adapter = cartAdapter
    }
}