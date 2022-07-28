package com.example.dpectrum

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.addCallback
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.example.dpectrum.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlin.system.exitProcess

@AndroidEntryPoint
class HomeFragment: Fragment() {

    private var _binding:FragmentHomeBinding?=null
    private val binding:FragmentHomeBinding
        get()=_binding!!

    private var backKeyPressedTime=0L

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding=FragmentHomeBinding.inflate(layoutInflater,container,false)
        binding.fragmentHomeBottomNavigationView.itemIconTintList=null

        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }
}