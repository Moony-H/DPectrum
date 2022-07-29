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
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.example.dpectrum.databinding.FragmentLoginBinding
import com.example.dpectrum.databinding.FragmentPasswordCertificationBinding
import com.example.dpectrum.util.Util
import com.example.dpectrum.viewmodels.LoginViewModel
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

    private val loginViewModel:LoginViewModel by hiltNavGraphViewModels(R.id.loginAndHomeNavigation)

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
        loginViewModel.token.observe(viewLifecycleOwner){
            if(it!="null"){
                Navigation.findNavController(binding.root)
                    .navigate(R.id.action_loginFragment_to_homeFragment)
            }else{
                Util.openBasicDialog(context,layoutInflater,"안내","휴대폰 번호\n혹은 비밀번호를 확인해 주세요")
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
            binding.fragmentLoginLoginButton-> {
                Log.d("LoginFragment","LoginButton clicked")
                if (checkConfirmEdittext()) {
                    Log.d("LoginFragment","LoginButton clicked and state all done")
                    loginViewModel.login(
                        binding.fragmentLoginPassword.getEdittextContent(),
                        binding.fragmentLoginPhoneNumber.getEdittextContent()
                    )
                }
            }

            binding.fragmentLoginSignUpButton->{
                Navigation.findNavController(binding.root).navigate(R.id.action_loginFragment_to_signUpCerFragment)
            }

            binding.fragmentLoginFindPasswordButton->{
                Navigation.findNavController(binding.root).navigate(R.id.action_loginFragment_to_passwordCerFragment)
            }
        }
    }

    private fun checkConfirmEdittext():Boolean{
        val phone=binding.fragmentLoginPhoneNumber
        val password=binding.fragmentLoginPassword

        if(phone.getEdittextContent()==""){
            phone.setErrorMessage("휴대폰 번호를 입력하세요")
            phone.setConfirmState(false)
        }else if(!phone.getEdittextContent().matches("""^(?=.*[0-9]).{11,}$""".toRegex())){
            phone.setErrorMessage("핸드폰 번호가 올바르지 않습니다.")
            phone.setConfirmState(false)
        }else{
            phone.setErrorMessage("")
            phone.setConfirmState(true)
        }

        if(password.getEdittextContent()==""){
            password.setErrorMessage("비밀번호를 입력하세요")
            password.setConfirmState(false)
        }else{
            password.setErrorMessage("")
            password.setConfirmState(true)
        }

        return phone.getConfirmState()&&password.getConfirmState()

    }
}