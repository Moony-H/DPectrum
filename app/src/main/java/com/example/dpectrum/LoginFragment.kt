package com.example.dpectrum

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.addCallback
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.example.dpectrum.databinding.FragmentLoginBinding
import com.example.dpectrum.databinding.FragmentPasswordCertificationBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import kotlin.system.exitProcess

@AndroidEntryPoint
class LoginFragment:Fragment(),View.OnClickListener{


    private var _binding: FragmentLoginBinding?=null
    private val binding: FragmentLoginBinding
        get()=_binding!!

    private var backKeyPressedTime=0L

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding= FragmentLoginBinding.inflate(inflater,container,false)

        binding.fragmentLoginLoginButton.setOnClickListener(this)
        binding.fragmentLoginFindPasswordButton.setOnClickListener(this)
        binding.fragmentLoginSignUpButton.setOnClickListener(this)
        val callback = requireActivity().onBackPressedDispatcher.addCallback(this) {
            if (System.currentTimeMillis() > backKeyPressedTime + 2500) {
                backKeyPressedTime = System.currentTimeMillis()
                Toast.makeText(context, "뒤로 가기 버튼을 한 번 더 누르시면 종료됩니다.", Toast.LENGTH_LONG).show()

            } else {
                ActivityCompat.finishAffinity(requireActivity())
                exitProcess(0)
            }
        }


        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }

    override fun onClick(view: View?) {
        when(view){
            binding.fragmentLoginLoginButton->{
                Navigation.findNavController(binding.root).navigate(R.id.action_loginFragment_to_homeFragment)
            }

            binding.fragmentLoginSignUpButton->{
                Navigation.findNavController(binding.root).navigate(R.id.action_loginFragment_to_signUpCerFragment)
            }

            binding.fragmentLoginFindPasswordButton->{
                Navigation.findNavController(binding.root).navigate(R.id.action_loginFragment_to_passwordCerFragment)
            }
        }
    }
}