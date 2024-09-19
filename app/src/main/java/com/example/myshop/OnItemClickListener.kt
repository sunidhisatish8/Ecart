package com.example.myshop

import com.example.myshop.model.data.Category
import com.example.myshop.model.data.Subcategory

interface OnItemClickListener {
    fun onCategoryClick(subCategory: String)
}