package com.example.myshop.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import com.example.myshop.Constants.PAYMENT_MODE
import com.example.myshop.PaymentMethod
import com.example.myshop.R
import com.example.myshop.databinding.FragmentPaymentBinding

class PaymentFragment : Fragment() {
    private lateinit var binding: FragmentPaymentBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPaymentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        selectPaymentMethod()
        binding.savedPaymentGroup.setOnCheckedChangeListener { radioGroup, i ->
            val radioButton = radioGroup.findViewById<RadioButton>(i)
            Bundle().putString(PAYMENT_MODE, radioButton.text.toString())
        }
        binding.btnCheckout.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.fragment_container, SummaryFragment())
                ?.addToBackStack(null)
                ?.commit()
        }
    }

    private fun selectPaymentMethod() {
        PaymentMethod.entries.forEach { methodType ->
            val radioButton = RadioButton(requireContext())
            radioButton.text = methodType.paymentMethod
            binding.savedPaymentGroup.addView(radioButton)
        }
    }
}