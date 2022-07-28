package com.example.dpectrum.api

import com.example.dpectrum.data.SignUpBody
import com.example.dpectrum.data.SignUpServiceResponse
import kotlinx.coroutines.Deferred
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface SignUpService {
    @POST("auth/join")
    suspend fun signUp(
        @Body
        signUpBody: SignUpBody
    ): Response<SignUpServiceResponse>
}