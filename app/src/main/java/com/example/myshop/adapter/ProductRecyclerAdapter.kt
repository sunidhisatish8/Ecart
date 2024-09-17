package com.example.myshop.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myshop.Constants.IMAGE_BASE_URL
import com.example.myshop.R
import com.example.myshop.data.Product
import com.example.myshop.databinding.ItemProductBinding
import com.example.myshop.fragments.CartFragment
import com.example.myshop.viewModel.CartViewModel
import com.squareup.picasso.Picasso

class ProductRecyclerAdapter(
    private val productList: List<Product>,
    private val cartViewModel: CartViewModel,
) :
    RecyclerView.Adapter<ProductRecyclerAdapter.ViewHolder>() {

    private lateinit var binding: ItemProductBinding

    inner class ViewHolder(private val binding: ItemProductBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(product: Product) {
            binding.apply {
                Picasso.get()
                    .load(IMAGE_BASE_URL)
                    .placeholder(R.drawable.phone)
                    .into(productImage)
                productDescription.text = product.description
                productName.text = product.productName
                productPrice.text = "$ ${product.price}"
                productRating.rating = product.averageRating.toFloat()
                cartViewModel.cartData.observeForever { cartItems ->
                    val cartItem = cartItems.find { it.name == product.productName }
                    if (cartItem != null) {
                        llQuantity.visibility = View.VISIBLE
                        btnAddToCart.visibility = View.GONE
                        tvQuantity.text = cartItem.quantity.toString()
                        btnDecrease.isEnabled = cartItem.quantity > 1
                    } else {
                        llQuantity.visibility = View.GONE
                        btnAddToCart.visibility = View.VISIBLE
                    }
                }

                btnAddToCart.setOnClickListener {
                    cartViewModel.addToCart(product)
                    updateCart(product)
                    notifyDataSetChanged()
                }
                btnIncrease.setOnClickListener {
                    cartViewModel.increaseQuantity(product)
                    updateCart(product)
                    notifyDataSetChanged()
                }
                btnDecrease.setOnClickListener {
                    cartViewModel.decreaseQuantity(product)
                    updateCart(product)
                    notifyDataSetChanged()
                }
            }
        }
    }

    fun updateCart(product: Product) {
        val cartItems = cartViewModel.cartData.value ?: return
        val currentItem = cartItems.find { it.name == product.productName }
        binding.apply {
            currentItem?.let {
                tvQuantity.text = it.quantity.toString()
                btnDecrease.isEnabled = it.quantity > 1
            }
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(productList[position])
    }

    override fun getItemCount(): Int = productList.size
}