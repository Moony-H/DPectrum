package com.example.dpectrum.api

import com.example.dpectrum.data.CertificateToken
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface PasswordCertificationService {
    @FormUrlEncoded
    @POST("auth/certificate/phone")
    suspend fun certificatePhone(
        @Field("phoneNumber")
        phoneNumber:String
    ):Response<CertificateToken>

}