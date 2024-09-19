package com.example.myshop.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myshop.databinding.ItemOrderDetailBinding
import com.example.myshop.model.data.ItemX

class OrderSummaryAdapter(private val items: List<ItemX>) :
    RecyclerView.Adapter<OrderSummaryAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemOrderDetailBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding =
            ItemOrderDetailBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.binding.apply {
            textProductName.text = "Product Name: ${item.productName}"
            textQuantity.text = "Quantity: ${item.quantity}"
            textUnitPrice.text = "Unit Price: ${item.unitPrice}"
            textAmount.text = "Amount: ${item.amount}"
        }
    }

    override fun getItemCount() = items.size
}