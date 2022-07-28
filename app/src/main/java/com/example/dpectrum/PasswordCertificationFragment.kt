package com.example.dpectrum

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.navigation.Navigation
import androidx.navigation.navGraphViewModels
import com.example.dpectrum.databinding.FragmentPasswordCertificationBinding
import com.example.dpectrum.viewmodels.PasswordViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PasswordCertificationFragment:Fragment(),View.OnClickListener {
    private var _binding: FragmentPasswordCertificationBinding?=null
    private val binding: FragmentPasswordCertificationBinding
        get()=_binding!!

    private val passwordViewModel:PasswordViewModel by hiltNavGraphViewModels(R.id.findPasswordNavigation)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding= FragmentPasswordCertificationBinding.inflate(inflater,container,false)
        binding.fragmentPasswordCerCerButton.setOnClickListener(this)
        binding.fragmentPasswordCerNextButton.setOnClickListener(this)

        return binding.root

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }

    override fun onClick(view: View?) {
        when(view){
            binding.fragmentPasswordCerCerButton->{
                Log.d("PasswordCertificationFragment","certification button clicked")
            }

            binding.fragmentPasswordCerNextButton->{
                Log.d("PasswordCertificationFragment","next button clicked")
                Navigation.findNavController(binding.root).navigate(R.id.action_passwordCerFragment_to_passwordNewFragment)
            }
        }
    }
}