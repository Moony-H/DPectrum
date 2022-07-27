package com.example.dpectrum

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.example.dpectrum.databinding.FragmentSplashBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*

@AndroidEntryPoint
class SplashFragment:Fragment() {
    private var _binding:FragmentSplashBinding?=null
    private val binding:FragmentSplashBinding
        get()=_binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        Log.d("test","splash")
        _binding=FragmentSplashBinding.inflate(inflater,container,false)
        lifecycleScope
        lifecycleScope.launch(Dispatchers.IO) {
            delay(2000)
            withContext(Dispatchers.Main){
                val navController= Navigation.findNavController(binding.root)
                navController.navigate(R.id.action_splashFragment_to_loginFragment)
            }

        }
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("test","splash destroy")
        _binding=null
    }
}