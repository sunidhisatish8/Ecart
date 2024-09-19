package com.example.myshop.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.myshop.MainActivity
import com.example.myshop.R
import com.example.myshop.databinding.FragmentOnBoardThreeBinding
import com.example.myshop.databinding.FragmentOnBoardTwoBinding
import com.example.myshop.databinding.FragmentOnBoardingOneBinding
import com.example.myshop.security.SharedPreferences

class OnBoardTwoFragment : Fragment() {
    private lateinit var binding: FragmentOnBoardTwoBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOnBoardTwoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.nextButton.setOnClickListener {
            SharedPreferences.saveBoolean(SharedPreferences.KEY_IS_NEXT, true)
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.onboard_fragment_container, OnBoardThreeFragment())
                ?.addToBackStack(null)
                ?.commit()
        }
        binding.skipButton.setOnClickListener {
            SharedPreferences.saveBoolean(SharedPreferences.KEY_IS_ONBOARD, true)
            val intent = Intent(requireContext(), MainActivity::class.java)
            startActivity(intent)
            activity?.finish()
        }
    }
}