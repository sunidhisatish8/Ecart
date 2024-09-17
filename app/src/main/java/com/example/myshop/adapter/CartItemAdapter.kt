package com.example.myshop.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myshop.data.CartItems
import com.example.myshop.databinding.ItemCartBinding

class CartItemAdapter() :
    RecyclerView.Adapter<CartItemAdapter.ViewHolder>() {
    private val cartList = mutableListOf<CartItems>()

    inner class ViewHolder(private val binding: ItemCartBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(cartItem: CartItems) {
            binding.productName.text = cartItem.name
            binding.productQuantity.text = "Quantity: ${cartItem.quantity}"
            binding.productPrice.text = "$ ${cartItem.price * cartItem.quantity}"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemCartBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = cartList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(cartList[position])
    }

    fun updateCartItems(newCartItems: List<CartItems>) {
        cartList.clear()
        cartList.addAll(newCartItems)
        notifyDataSetChanged()
    }
}