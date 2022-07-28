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
import com.example.dpectrum.data.SignUpBody
import com.example.dpectrum.databinding.FragmentPasswordCertificationBinding
import com.example.dpectrum.databinding.FragmentSignUpCertificationBinding
import com.example.dpectrum.databinding.FragmentSignUpInfoBinding
import com.example.dpectrum.viewmodels.SignUpViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpCertificationFragment: Fragment(),View.OnClickListener {
    private var _binding: FragmentSignUpCertificationBinding?=null
    private val binding: FragmentSignUpCertificationBinding
        get()=_binding!!

    private val signUpViewModel: SignUpViewModel by hiltNavGraphViewModels(R.id.signUpNavigation)

    private var cerButtonClicked=false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding= FragmentSignUpCertificationBinding.inflate(inflater,container,false)
        binding.fragmentSignUpCerNextButton.setOnClickListener(this)
        binding.fragmentSignUpCerCerButton.setOnClickListener(this)

        binding.fragmentSignUpCerPhoneNumber.setEdittextContentErrorCondition {
            if(it.isEmpty()){
                binding.fragmentSignUpCerPhoneNumber.setErrorMessage("")

                false
            }else if(it.length<11){
                binding.fragmentSignUpCerPhoneNumber.setErrorMessage("핸드폰 번호가 올바르지 않습니다.")
                false
            }else{
                binding.fragmentSignUpCerPhoneNumber.setErrorMessage("")
                true
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
            binding.fragmentSignUpCerCerButton->{
                Log.d("SignUpCertificationFragment","certification Button Clicked")
                val string=binding.fragmentSignUpCerPhoneNumber.getEdittextContent()
                if(string==""){
                    binding.fragmentSignUpCerPhoneNumber.setErrorMessage("휴대폰번호를 입력하세요")
                }else if (string.length<11){
                    binding.fragmentSignUpCerPhoneNumber.setErrorMessage("핸드폰 번호가 올바르지 않습니다.")
                }else{
                    binding.fragmentSignUpCerPhoneNumber.setErrorMessage("인증번호가 전송되었습니다.")
                    cerButtonClicked=true
                }


            }

            binding.fragmentSignUpCerNextButton->{
                Log.d("SignUpCertificationFragment","next Button Clicked")
                if(cerButtonClicked){
                    if(binding.fragmentSignUpCerCerNumber.getEdittextContent()=="123123"){
                        Navigation.findNavController(binding.root).navigate(R.id.action_signUpCerFragment_to_signUpInfoFragment)
                        signUpViewModel.setMemberInfo(SignUpBody("",binding.fragmentSignUpCerPhoneNumber.getEdittextContent(),"",""))
                    } else{
                        binding.fragmentSignUpCerCerNumber.setErrorMessage("인증번호가 올바르지 않습니다.")
                    }
                }else{
                    binding.fragmentSignUpCerPhoneNumber.setErrorMessage("핸드폰 번호를 인증해 주세요.")
                }

            }
        }
    }
}