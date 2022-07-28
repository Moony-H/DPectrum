package com.example.dpectrum

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.navigation.Navigation
import com.example.dpectrum.data.SignUpBody
import com.example.dpectrum.databinding.FragmentSignUpInfoBinding
import com.example.dpectrum.tag.SignUpResponseTag
import com.example.dpectrum.viewmodels.SignUpViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpInfoFragment: Fragment() {
    private var _binding: FragmentSignUpInfoBinding?=null
    private val binding: FragmentSignUpInfoBinding
        get()=_binding!!

    private val signUpViewModel:SignUpViewModel by hiltNavGraphViewModels(R.id.signUpNavigation)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding= FragmentSignUpInfoBinding.inflate(inflater,container,false)

        //toolbar setting

        val toolbar = binding.fragmentSignUpInfoToolbar
        toolbar.setNavigationIcon(R.drawable.arrow_back_48px)
        toolbar.setNavigationOnClickListener {
            Navigation.findNavController(binding.root).navigate(R.id.action_signUpInfoFragment_to_signUpCerFragment)
        }

        binding.fragmentSignUpInfoName.setEdittextContentErrorCondition(confirmEditTextCondition)
        binding.fragmentSignUpInfoSchool.setEdittextContentErrorCondition(confirmEditTextCondition)
        binding.fragmentSignUpInfoPassword.setEdittextContentErrorCondition(confirmEditTextCondition)
        binding.fragmentSignUpInfoPasswordCer.setEdittextContentErrorCondition(confirmEditTextCondition)

        signUpViewModel.signUpResponseTag.observe(viewLifecycleOwner){
            if(it==SignUpResponseTag.RESPONSE_SUCCESS){
                Toast.makeText(context,"회원가입이 완료되었습니다.",Toast.LENGTH_SHORT).show()
                Navigation.findNavController(binding.root).navigate(R.id.action_signUpInfoFragment_to_loginFragment)
            }
        }

        binding.fragmentSignUpInfoSignUpButton.setOnClickListener {
            val nameState=binding.fragmentSignUpInfoName.getConfirmState()
            val schoolState=binding.fragmentSignUpInfoSchool.getConfirmState()
            val passwordState=binding.fragmentSignUpInfoPassword.getConfirmState()
            val passwordCerState=binding.fragmentSignUpInfoPasswordCer.getConfirmState()
            if(!nameState){
                binding.fragmentSignUpInfoName.setErrorMessage("이름을 입력해 주세요")
            }else{
                binding.fragmentSignUpInfoName.setErrorMessage("")
            }
            if(!schoolState){
                binding.fragmentSignUpInfoSchool.setErrorMessage("학교를 입력해 주세요")
            }else{
                binding.fragmentSignUpInfoSchool.setErrorMessage("")
            }
            if(!passwordState){
                binding.fragmentSignUpInfoPassword.setErrorMessage("비밀번호를 입력해 주세요")
            }else{
                binding.fragmentSignUpInfoPassword.setErrorMessage("")
            }
            if(!passwordCerState){
                binding.fragmentSignUpInfoPasswordCer.setErrorMessage("비밀번호 확인을 입력해 주세요")
            }else{
                binding.fragmentSignUpInfoPasswordCer.setErrorMessage("")
            }

            if(nameState && schoolState && passwordState && passwordCerState){
                val info=SignUpBody(
                    binding.fragmentSignUpInfoPassword.getEdittextContent(),
                    "123123",
                    binding.fragmentSignUpInfoName.getEdittextContent(),
                    binding.fragmentSignUpInfoSchool.getEdittextContent()
                )
                signUpViewModel.setMemberInfo(info)
                signUpViewModel.signUp()
            }


        }


        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }

    private val confirmEditTextCondition={string:String->
        string != ""
    }
}