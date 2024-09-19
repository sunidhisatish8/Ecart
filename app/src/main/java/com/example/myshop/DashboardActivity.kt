package com.example.myshop

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.SearchView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.transition.Visibility
import com.example.myshop.Constants.IS_SEARCH_CLICKED
import com.example.myshop.Constants.MY_SHOP
import com.example.myshop.Constants.PREF_FILE_NAME
import com.example.myshop.Constants.SEARCH_TEXT
import com.example.myshop.databinding.ActivityDashboardBinding
import com.example.myshop.databinding.ActivityMainBinding
import com.example.myshop.fragments.AddressFragment
import com.example.myshop.fragments.CartFragment
import com.example.myshop.fragments.CategoryFragment
import com.example.myshop.fragments.SubCategoryProductFragment
import com.example.myshop.fragments.SummaryFragment
import com.example.myshop.model.remote.ApiClient
import com.example.myshop.model.remote.ApiService
import com.example.myshop.security.SharedPreferences

class DashboardActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDashboardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        setUserDetails()
        setToolbar()
        initViews()
        binding.btnSearchTool.setOnClickListener {
            binding.searchEditText.visibility = View.VISIBLE
            binding.searchButton.visibility = View.VISIBLE
            searchProduct()
        }
    }

    private fun setUserDetails() {
        val headerView = binding.navView.getHeaderView(0)
        val emailIdTextView = headerView.findViewById<TextView>(R.id.emailId)
        val emailId = SharedPreferences.getString(SharedPreferences.KEY_EMAIL, "example@email.com")
        emailIdTextView.text = "Hi, $emailId"
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
                    .addToBackStack(null)
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
                R.id.home -> {
                    replaceFragment(CategoryFragment())
                }
                R.id.cart -> {
                    replaceFragment(CartFragment())
                }
                R.id.orders -> {
                    replaceFragment(SummaryFragment())
                }
                R.id.address -> {
                    replaceFragment(AddressFragment())
                }
                R.id.logout -> {
                    showLogoutConfirmationDialog()
                }
                else -> {
                    replaceFragment(CategoryFragment())
                }
            }

            binding.drawerLayout.closeDrawer(GravityCompat.START)
            true
        }
    }
    private fun showLogoutConfirmationDialog() {
        AlertDialog.Builder(this)
            .setTitle("Confirm Logout")
            .setMessage("Are you sure you want to logout?")
            .setPositiveButton("Yes") { dialog, _ ->
                logout()
                dialog.dismiss()
            }
            .setNegativeButton("No") { dialog, _ ->
                dialog.dismiss()
            }
            .create()
            .show()
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }

    private fun initViews() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, CategoryFragment())
            .addToBackStack(null)
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

    private fun logout() {
        val sharedPreferences = getSharedPreferences(PREF_FILE_NAME, MODE_PRIVATE)
        with(sharedPreferences.edit()) {
            clear()
            apply()
        }
        SharedPreferences.saveBoolean(SharedPreferences.KEY_IS_ONBOARD, true)
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }
}