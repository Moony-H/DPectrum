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
import com.example.dpectrum.databinding.FragmentPasswordCertificationBinding
import com.example.dpectrum.viewmodels.PasswordViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PasswordCertificationFragment:Fragment(),View.OnClickListener {
    private var _binding: FragmentPasswordCertificationBinding?=null
    private val binding: FragmentPasswordCertificationBinding
        get()=_binding!!

    private val passwordViewModel:PasswordViewModel by hiltNavGraphViewModels(R.id.findPasswordNavigation)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding= FragmentPasswordCertificationBinding.inflate(inflater,container,false)
        binding.fragmentPasswordCerCerButton.setOnClickListener(this)
        binding.fragmentPasswordCerNextButton.setOnClickListener(this)

        return binding.root

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }

    override fun onClick(view: View?) {
        when(view){
            binding.fragmentPasswordCerCerButton->{
                Log.d("PasswordCertificationFragment","certification button clicked")
                val phone=binding.fragmentPasswordCerPhoneNumber
                if(phone.getEdittextContent()==""){
                    phone.setErrorMessage("휴대폰번호를 입력하세요")
                    phone.setConfirmState(false)
                }else if(!phone.getEdittextContent().matches("""^(?=.*[0-9]).{11,20}""".toRegex())){
                    phone.setErrorMessage("핸드폰 번호가 올바르지 않습니다.")
                    phone.setConfirmState(false)
                }else{
                    phone.setErrorMessage("인증번호가 전송되었습니다.")
                    phone.setConfirmState(true)
                }
            }

            binding.fragmentPasswordCerNextButton->{
                Log.d("PasswordCertificationFragment","next button clicked")
                if(!binding.fragmentPasswordCerPhoneNumber.getConfirmState()){
                    binding.fragmentPasswordCerPhoneNumber.setErrorMessage("핸드폰 번호를 인증해주세요")

                }else if(binding.fragmentPasswordCerCerNumber.getEdittextContent()!="123123"){
                    binding.fragmentPasswordCerCerNumber.setErrorMessage("인증번호가 올바르지 않습니다.")
                }else{
                    Navigation.findNavController(binding.root).navigate(R.id.action_passwordCerFragment_to_passwordNewFragment)
                }

            }
        }
    }
}