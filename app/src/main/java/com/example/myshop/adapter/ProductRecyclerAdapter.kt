package com.example.myshop.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myshop.data.Product
import com.example.myshop.databinding.ItemProductBinding

class ProductRecyclerAdapter(private val productList: List<Product>) :
    RecyclerView.Adapter<ProductRecyclerAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: ItemProductBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(product: Product) {
            binding.productDescription.text = product.description
            binding.productName.text = product.product_name
            binding.productPrice.text = "$ ${product.price}"
            binding.productRating.rating = product.average_rating.toFloat()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(productList[position])
    }

    override fun getItemCount(): Int = productList.size
}