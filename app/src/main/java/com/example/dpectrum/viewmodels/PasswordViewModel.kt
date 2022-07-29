package com.example.dpectrum.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dpectrum.data.CertificateToken
import com.example.dpectrum.data.PasswordCertificationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PasswordViewModel @Inject constructor(private val passwordCertificationRepository: PasswordCertificationRepository):ViewModel() {

    private val _token=MutableLiveData<CertificateToken>()
    val token:LiveData<CertificateToken>
        get()=_token

    fun setToken(phone:String){
        viewModelScope.launch(Dispatchers.IO){
            val response=passwordCertificationRepository.getPasswordCerToken(phone)
            if(response.code()==200){
                _token.postValue(response.body())
            }else if(response.code()==400){

            }
        }
    }

}