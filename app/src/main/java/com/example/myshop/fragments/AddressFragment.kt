package com.example.myshop.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.lifecycle.ViewModelProvider
import com.example.myshop.databinding.FragmentAddressBinding
import com.example.myshop.model.data.DeliveryAddress
import com.example.myshop.model.data.UserAddress
import com.example.myshop.security.SharedPreferences
import com.example.myshop.viewModel.AddressViewModel

class AddressFragment : Fragment() {
    private lateinit var binding: FragmentAddressBinding
    private lateinit var userAddress: UserAddress
    private lateinit var viewModel: AddressViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddressBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val userId = SharedPreferences.getString(SharedPreferences.KEY_USER_ID, null.toString())
        viewModel = ViewModelProvider(requireActivity())[AddressViewModel::class.java]
        binding.apply {
            plusButton.setOnClickListener {
                addressForm.visibility = View.VISIBLE
                saveButton.setOnClickListener {
                    userAddress = UserAddress(
                        addressEditText.text.toString(),
                        titleEditText.text.toString(),
                        userId.toInt()
                    )
                    updateUserAddress(userAddress)
                    addressForm.visibility = View.GONE
                    addressEditText.text.clear()
                    titleEditText.text.clear()
                }
                cancelButton.setOnClickListener {
                    addressForm.visibility = View.GONE
                }
            }
        }
        selectExistingUserAddress(userId)
        binding.savedAddressesGroup.setOnCheckedChangeListener { radioGroup, i ->
            val radioButton = radioGroup.findViewById<RadioButton>(i)
            val selectedAddressData = radioButton.tag as? DeliveryAddress
            selectedAddressData?.let { data ->
                viewModel.setSelectedAddress(data)
            }
        }
    }

    private fun selectExistingUserAddress(userId: String) {
        viewModel.getAddressDetails(userId)
        viewModel.existingAddressResponse.observe(viewLifecycleOwner) { addressList ->
            binding.savedAddressesGroup.removeAllViews()
            addressList.addresses.forEach { address ->
                val radioButton = RadioButton(requireContext())
                radioButton.text = address.address
                radioButton.tag = DeliveryAddress(address.address, address.title)
                binding.savedAddressesGroup.addView(radioButton)
            }
        }
    }

    private fun updateUserAddress(userAddress: UserAddress) {
        viewModel.addProductDetails(userAddress)
        viewModel.getAddressDetails(SharedPreferences.getString(SharedPreferences.KEY_USER_ID, ""))
    }
}