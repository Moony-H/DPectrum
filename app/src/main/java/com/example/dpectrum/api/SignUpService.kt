package com.example.dpectrum.api

import com.example.dpectrum.data.SignUpBody
import com.example.dpectrum.data.SignUpServiceResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface SignUpService {
    @POST("auth/join")
    suspend fun signUp(
        @Body
        signUpBody: SignUpBody
    ): Response<SignUpServiceResponse>
}