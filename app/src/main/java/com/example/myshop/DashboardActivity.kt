package com.example.myshop

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.SearchView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.transition.Visibility
import com.example.myshop.Constants.IS_SEARCH_CLICKED
import com.example.myshop.Constants.MY_SHOP
import com.example.myshop.Constants.SEARCH_TEXT
import com.example.myshop.databinding.ActivityDashboardBinding
import com.example.myshop.databinding.ActivityMainBinding
import com.example.myshop.fragments.CategoryFragment
import com.example.myshop.fragments.SubCategoryProductFragment
import com.example.myshop.remote.ApiClient
import com.example.myshop.remote.ApiService

class DashboardActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDashboardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        setToolbar()
        initViews()
        binding.btnSearchTool.setOnClickListener {
            binding.searchEditText.visibility = View.VISIBLE
            binding.searchButton.visibility = View.VISIBLE
            searchProduct()
        }
    }

    private fun searchProduct() {
        binding.searchButton.setOnClickListener {
            val query = binding.searchEditText.text.toString()
            if (query.isNotEmpty()) {
                IS_SEARCH_CLICKED = true
                val subCategoryFragment = SubCategoryProductFragment().apply {
                    arguments = Bundle().apply {
                        putString(SEARCH_TEXT, query)
                    }
                }
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, subCategoryFragment)
                    .addToBackStack("SubCategoryProductFragment")
                    .commit()
            }
        }
    }

    private fun setToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            title = MY_SHOP
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.baseline_menu_24)
        }
        binding.navView.setNavigationItemSelectedListener { menuItems ->
            menuItems.isChecked = true
            when (menuItems.itemId) {
                R.id.home -> showMessage("Home", "Selected")
            }
            true
        }
    }

    private fun initViews() {
        val fragment = CategoryFragment()
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack("CategoryFragment")
            .commit()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
                binding.drawerLayout.closeDrawer(GravityCompat.START)
            } else {
                binding.drawerLayout.openDrawer(GravityCompat.START)
            }
        }
        return super.onOptionsItemSelected(item)
    }

}