package com.example.dpectrum

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.example.dpectrum.data.SignUpBody
import com.example.dpectrum.databinding.FragmentSignUpInfoBinding
import com.example.dpectrum.tag.SignUpResponseTag
import com.example.dpectrum.util.Util
import com.example.dpectrum.viewmodels.SignUpViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpInfoFragment: Fragment() {
    private var _binding: FragmentSignUpInfoBinding?=null
    private val binding: FragmentSignUpInfoBinding
        get()=_binding!!

    private val args: SignUpInfoFragmentArgs by navArgs()


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

        //args
        val info=SignUpBody(
            "",
            args.phone,
            "",
            ""
        )
        signUpViewModel.setSignUpBody(info)

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

            if(checkAllConfirmEditText()){
                if(binding.fragmentSignUpInfoTermService.isChecked && binding.fragmentSignUpInfoTermPrivacy.isChecked){
                    val info=SignUpBody(
                        binding.fragmentSignUpInfoPassword.getEdittextContent(),
                        signUpViewModel.memberInfo.value!!.phoneNumber,
                        binding.fragmentSignUpInfoName.getEdittextContent(),
                        binding.fragmentSignUpInfoSchool.getEdittextContent()
                    )
                    signUpViewModel.setSignUpBody(info)
                    signUpViewModel.signUp()
                }else{
                    Util.openBasicDialog(context,layoutInflater,"안내","이용약관과 개인정보처리방침에\n 동의해 주세요")
                }

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



    private fun checkAllConfirmEditText():Boolean {
        val name = binding.fragmentSignUpInfoName
        val school = binding.fragmentSignUpInfoSchool
        val password = binding.fragmentSignUpInfoPassword
        val passwordCer = binding.fragmentSignUpInfoPasswordCer

        if (name.getEdittextContent() == "") {
            name.setErrorMessage("이름을 입력해 주세요")
            name.setConfirmState(false)
        } else {
            name.setErrorMessage("")
            name.setConfirmState(true)
        }

        if (school.getEdittextContent() == "") {
            school.setErrorMessage("학교를 입력해 주세요")
            school.setConfirmState(false)
        } else {
            school.setErrorMessage("")
            school.setConfirmState(true)
        }

        if (password.getEdittextContent() == "") {
            password.setErrorMessage("비밀번호를 입력해 주세요")
            password.setConfirmState(false)
        } else if (!password.getEdittextContent()
                .matches("""^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[!@#$%^+\-=])(?=\S+$).{8,20}$""".toRegex())

        ) {
            password.setErrorMessage("특문, 숫자포함 8자리 이상 입력해 주세요")
            password.setConfirmState(false)
        } else {
            password.setErrorMessage("")
            password.setConfirmState(true)
        }

        if (passwordCer.getEdittextContent() == "") {
            passwordCer.setErrorMessage("비밀번호 확인을 입력해 주세요")
            passwordCer.setConfirmState(false)
        } else if (password.getEdittextContent() != passwordCer.getEdittextContent()) {

            passwordCer.setErrorMessage("비밀번호가 일치하지 않습니다.")
            passwordCer.setConfirmState(false)
        } else {
            passwordCer.setErrorMessage("")
            passwordCer.setConfirmState(true)
        }

        return name.getConfirmState() && school.getConfirmState() && password.getConfirmState() && passwordCer.getConfirmState()

    }
}