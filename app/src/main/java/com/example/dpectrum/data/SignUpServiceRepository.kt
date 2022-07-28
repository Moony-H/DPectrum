package com.example.dpectrum.data

import android.util.Log
import com.example.dpectrum.api.SignUpService
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SignUpServiceRepository @Inject constructor(private val signUpService: SignUpService) {

    suspend fun postSignUpBody(signUpBody: SignUpBody):Response<SignUpServiceResponse>{
        return signUpService.signUp(signUpBody)
    }
}