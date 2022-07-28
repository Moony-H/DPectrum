package com.example.dpectrum.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dpectrum.api.SignUpService
import com.example.dpectrum.data.MemberInfo
import com.example.dpectrum.data.SignUpBody
import com.example.dpectrum.data.SignUpServiceRepository
import com.example.dpectrum.tag.SignUpResponseTag
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(private val signUpServiceRepository: SignUpServiceRepository):ViewModel() {

    private val _memberInfo=MutableLiveData<SignUpBody>()
    val memberInfo: LiveData<SignUpBody>
        get() = _memberInfo

    private val _signUpResponseTag=MutableLiveData<SignUpResponseTag>()
    val signUpResponseTag:LiveData<SignUpResponseTag>
        get()=_signUpResponseTag

    fun setMemberInfo(signUpBody: SignUpBody){
        _memberInfo.value=signUpBody
    }
    fun signUp(){
        viewModelScope.launch(Dispatchers.IO) {
            memberInfo.value?.let {info->
                val response=signUpServiceRepository.postSignUpBody(info)
                Log.d("test","response Body: ${response.body()}")
                when(response.code()){
                    SignUpResponseTag.RESPONSE_SUCCESS.num->{
                        Log.d("SignUpViewModel","sign up response success: ${response.body()}")
                        _signUpResponseTag.postValue(SignUpResponseTag.RESPONSE_SUCCESS)
                    }
                    SignUpResponseTag.RESPONSE_DUPLICATED.num->{
                        Log.d("SignUpViewModel","sign up response duplicated: ${response.body()}")
                        _signUpResponseTag.postValue(SignUpResponseTag.RESPONSE_DUPLICATED)
                    }
                    SignUpResponseTag.RESPONSE_NO_PARAMS.num->{
                        Log.d("SignUpViewModel","sign up response no params: ${response.body()}")
                        _signUpResponseTag.postValue(SignUpResponseTag.RESPONSE_NO_PARAMS)
                    }
                    else->{
                        Log.d("SignUpViewModel","sign up response not found: ${response.code()}")
                    }
                }

            }

        }
    }
}