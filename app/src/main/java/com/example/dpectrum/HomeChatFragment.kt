package com.example.dpectrum

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.dpectrum.databinding.FragmentHomeChatBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeChatFragment:Fragment() {
    private var _binding: FragmentHomeChatBinding?=null
    private val binding: FragmentHomeChatBinding
        get()=_binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding= FragmentHomeChatBinding.inflate(inflater,container,false)

        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }
}