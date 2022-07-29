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
import androidx.navigation.navGraphViewModels
import com.example.dpectrum.databinding.FragmentPasswordCertificationBinding
import com.example.dpectrum.util.Util
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


        passwordViewModel.token.observe(viewLifecycleOwner) {
            if (it == "400 error") {
                Util.openConfirmDialog(
                    requireContext(),
                    layoutInflater,
                    "알림",
                    "가입된 정보가 없습니다.\n회원가입하시겠습니까?",
                    {
                        val phone= binding.fragmentPasswordCerPhoneNumber.getEdittextContent()
                        PasswordCertificationFragmentDirections.actionPasswordCerFragmentToSignUpInfoFragment(phone)
                        Navigation.findNavController(binding.root).navigate(R.id.action_passwordCerFragment_to_signUpInfoFragment)

                    },
                ){dialog->
                    dialog.dismiss()
                }
            }else if(it =="404 error"){
                Toast.makeText(requireContext(),"서버가 불안정합니다. 잠시후 다시 시도해 주세요",Toast.LENGTH_SHORT).show()
            }else{
                passwordViewModel.token.removeObservers(viewLifecycleOwner)
                Navigation.findNavController(binding.root).navigate(R.id.action_passwordCerFragment_to_passwordNewFragment)

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
                    passwordViewModel.setToken(binding.fragmentPasswordCerPhoneNumber.getEdittextContent())
                }

            }
        }
    }
}