package com.example.dpectrum

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.navGraphViewModels
import com.example.dpectrum.databinding.FragmentPasswordCertificationBinding
import com.example.dpectrum.databinding.FragmentPasswordNewBinding
import com.example.dpectrum.util.Util
import com.example.dpectrum.viewmodels.PasswordViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PasswordNewFragment:Fragment() {
    private var _binding: FragmentPasswordNewBinding?=null
    private val binding: FragmentPasswordNewBinding
        get()=_binding!!

    private val passwordViewModel: PasswordViewModel by hiltNavGraphViewModels(R.id.findPasswordNavigation)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding= FragmentPasswordNewBinding.inflate(inflater,container,false)

        passwordViewModel.passwordChanged.observe(viewLifecycleOwner){
            if(it=="200"){
                Toast.makeText(requireContext(),"비밀번호 변경이 완료 되었습니다.",Toast.LENGTH_SHORT).show()
                Navigation.findNavController(binding.root).navigate(R.id.action_passwordNewFragment_to_loginFragment)
            }
        }
        binding.fragmentPasswordNewCompleteButton.setOnClickListener{
            Log.d("PasswordNewFragment","complete button clicked")
            val password=binding.fragmentPasswordNewNewPassword
            val passwordCon=binding.fragmentPasswordNewNewPasswordConfirm

            if(password.getEdittextContent()==""){
                password.setErrorMessage("비밀번호를 입력해 주세요")
                password.setConfirmState(false)
            }else if(!password.getEdittextContent()
                    .matches("""^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[!@#$%^+\-=])(?=\S+$).{8,20}$""".toRegex())){
                password.setErrorMessage("특문, 숫자포함 8자리 이상 입력해 주세요")
                password.setConfirmState(false)
            }else{
                password.setErrorMessage("")
                password.setConfirmState(true)
            }

            if(passwordCon.getEdittextContent()==""){
                passwordCon.setErrorMessage("비밀번호를 입력해 주세요")
                passwordCon.setConfirmState(false)
            }else if(password.getEdittextContent()!=passwordCon.getEdittextContent()){
                passwordCon.setErrorMessage("비밀번호가 일치하지 않습니다.")
                passwordCon.setConfirmState(false)
            }else{
                passwordCon.setConfirmState(true)
            }

            if(password.getConfirmState()&&passwordCon.getConfirmState()){
                passwordViewModel.changePassword(password.getEdittextContent())
            }
        }



        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }
}