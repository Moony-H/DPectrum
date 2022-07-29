package com.example.dpectrum

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.dpectrum.databinding.FragmentHomeHomeBinding
import com.example.dpectrum.databinding.FragmentHomeTutorBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeTutorFragment:Fragment() {
    private var _binding: FragmentHomeTutorBinding?=null
    private val binding: FragmentHomeTutorBinding
        get()=_binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding= FragmentHomeTutorBinding.inflate(inflater,container,false)

        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }
}