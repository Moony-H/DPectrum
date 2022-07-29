package com.example.dpectrum

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.navigation.Navigation
import com.example.dpectrum.databinding.FragmentHomeDetailBinding
import com.example.dpectrum.util.Util
import com.example.dpectrum.viewmodels.LoginViewModel

class HomeDetailFragment:Fragment() {


    private var _binding:FragmentHomeDetailBinding?=null
    private val binding:FragmentHomeDetailBinding
        get()=_binding!!

    private val viewModel:LoginViewModel by hiltNavGraphViewModels(R.id.loginAndHomeNavigation)


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding=FragmentHomeDetailBinding.inflate(layoutInflater,container,false)

        val toolbar = binding.fragmentHomeDetailToolbar
        toolbar.setNavigationIcon(R.drawable.arrow_back_48px)
        toolbar.setNavigationOnClickListener {
            Navigation.findNavController(binding.root).navigate(R.id.action_homeDetailFragment_to_homeFragment)
        }
        viewModel.selectedContent.value?.let {

            binding.fragmentHomeDetailMainImage.setImageBitmap(viewModel.photo)
            binding.fragmentHomeDetailTutorName.text=it.tutorName
            val tutorDepartment=it.tutorSchool +" "+ it.tutorDepartment
            binding.fragmentHomeDetailTutorSchool.text=tutorDepartment
        }

        binding.fragmentHomeDetailMainImage.setOnClickListener {
            Navigation.findNavController(binding.root).navigate(R.id.action_homeDetailFragment_to_homeDetailPhotoFragment)
        }


        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }

}