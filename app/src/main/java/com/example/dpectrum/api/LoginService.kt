package com.example.dpectrum.api

import com.example.dpectrum.data.AccessToken
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface LoginService {
    @FormUrlEncoded
    @POST("auth/login/phone")
    suspend fun login(
        @Field("password") password:String,
        @Field("phoneNumber") phoneNumber:String
    ): Response<AccessToken>
}