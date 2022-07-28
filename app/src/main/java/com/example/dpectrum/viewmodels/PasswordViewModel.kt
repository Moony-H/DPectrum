package com.example.dpectrum.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PasswordViewModel @Inject constructor():ViewModel() {

    override fun onCleared() {
        super.onCleared()
        Log.d("PasswordViewModel","onCleared")
    }

}