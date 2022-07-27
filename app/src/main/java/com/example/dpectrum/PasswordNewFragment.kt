package com.example.dpectrum

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.dpectrum.databinding.FragmentPasswordCertificationBinding
import com.example.dpectrum.databinding.FragmentPasswordNewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PasswordNewFragment:Fragment() {
    private var _binding: FragmentPasswordNewBinding?=null
    private val binding: FragmentPasswordNewBinding
        get()=_binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding= FragmentPasswordNewBinding.inflate(inflater,container,false)

        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }
}