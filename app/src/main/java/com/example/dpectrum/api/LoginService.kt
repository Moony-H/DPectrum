package com.example.dpectrum.api

import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface LoginService {
    @FormUrlEncoded
    @POST("auth/login.phone")
    fun login(
        @Field("password") password:String,
        @Field("phoneNumber") phoneNumber:String
    )
}