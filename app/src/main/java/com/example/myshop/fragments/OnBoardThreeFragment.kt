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
import com.example.myshop.security.SharedPreferences

class OnBoardThreeFragment : Fragment() {
    private lateinit var binding: FragmentOnBoardThreeBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOnBoardThreeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        SharedPreferences.saveBoolean(SharedPreferences.KEY_IS_NEXT, true)
        super.onViewCreated(view, savedInstanceState)
        binding.nextButton.setOnClickListener {
            SharedPreferences.saveBoolean(SharedPreferences.KEY_IS_ONBOARD, true)
            val intent = Intent(requireContext(), MainActivity::class.java)
            startActivity(intent)
            activity?.finish()
        }
    }
}