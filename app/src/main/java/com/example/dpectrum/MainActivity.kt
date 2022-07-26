package com.example.dpectrum

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.dpectrum.databinding.ActivityMainBinding
import com.example.dpectrum.databinding.FragmentLoginBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity:AppCompatActivity() {

    private lateinit var _binding:FragmentLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding=FragmentLoginBinding.inflate(layoutInflater)
        setContentView(_binding.root)

    }
}