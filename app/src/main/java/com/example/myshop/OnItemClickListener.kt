package com.example.myshop

import com.example.myshop.data.Category
import com.example.myshop.data.Subcategory

interface OnItemClickListener {
    fun onCategoryClick(subCategory: String)
}