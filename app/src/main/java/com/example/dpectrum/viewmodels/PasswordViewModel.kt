package com.example.dpectrum.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dpectrum.data.CertificateToken
import com.example.dpectrum.data.PasswordCertificationRepository
import com.example.dpectrum.other.SingleLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PasswordViewModel @Inject constructor(private val passwordCertificationRepository: PasswordCertificationRepository):ViewModel() {

    private val _token= SingleLiveData<String>()
    val token:LiveData<String>
        get()=_token

    private val _passwordChanged=SingleLiveData<String>()
    val passwordChanged:LiveData<String>
        get()=_passwordChanged


    fun setToken(phone:String){
        viewModelScope.launch(Dispatchers.IO+coroutineExceptionHandler){
            val response=passwordCertificationRepository.getPasswordCerToken(phone)
            if(response.code()==200){
                Log.d("PasswordViewModel","set token success")
                _token.postValue(response.body()?.token)
            }else if(response.code()==400){
                Log.d("PasswordViewModel","set token failed: ${response.code()}")
                _token.postValue("400 error")
            }else{
                Log.d("PasswordViewModel","set token failed: ${response.code()}")
                _token.postValue("404 error")
            }
        }
    }

    fun changePassword(password:String){
        viewModelScope.launch(Dispatchers.IO+coroutineExceptionHandler){
            _token.value?.let {
                val response= passwordCertificationRepository.changePassword("Bearer $it",password)
                val id=response.body()?.id
                if(response.code()==200){
                    Log.d("PasswordViewModel","change success")
                    _passwordChanged.postValue("200")
                }else{
                    Log.d("PasswordViewModel","change failed ${response.code()}")
                    _passwordChanged.postValue("${response.code()}")
                }
            }



        }
    }

    private val coroutineExceptionHandler= CoroutineExceptionHandler{_, throwable->
        throwable.printStackTrace()

    }

}