package com.example.dpectrum

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.navigation.Navigation
import com.example.dpectrum.databinding.FragmentHomeDetailPhotoBinding
import com.example.dpectrum.viewmodels.LoginViewModel

class HomeDetailPhotoFragment:Fragment() {

    private val viewModel:LoginViewModel by hiltNavGraphViewModels(R.id.loginAndHomeNavigation)

    private var _binding:FragmentHomeDetailPhotoBinding?=null
    private val binding:FragmentHomeDetailPhotoBinding
        get()=_binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding=FragmentHomeDetailPhotoBinding.inflate(layoutInflater,container,false)
        binding.fragmentHomeDetailPhotoClose.setOnClickListener {
            Navigation.findNavController(binding.root).navigate(R.id.action_homeDetailPhotoFragment_to_homeDetailFragment)
        }
        binding.fragmentHomeDetailPhotoPhoto.setImageBitmap(viewModel.photo)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }
}