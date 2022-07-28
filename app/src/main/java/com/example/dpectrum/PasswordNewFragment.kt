package com.example.dpectrum

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.navGraphViewModels
import com.example.dpectrum.databinding.FragmentPasswordCertificationBinding
import com.example.dpectrum.databinding.FragmentPasswordNewBinding
import com.example.dpectrum.viewmodels.PasswordViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PasswordNewFragment:Fragment() {
    private var _binding: FragmentPasswordNewBinding?=null
    private val binding: FragmentPasswordNewBinding
        get()=_binding!!

    private val passwordViewModel: PasswordViewModel by hiltNavGraphViewModels(R.id.findPasswordNavigation)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding= FragmentPasswordNewBinding.inflate(inflater,container,false)
        binding.fragmentPasswordNewCompleteButton.setOnClickListener{
            Log.d("PasswordNewFragment","complete button clicked")
            Navigation.findNavController(binding.root).navigate(R.id.action_passwordNewFragment_to_loginFragment)
        }


        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }
}