package com.example.dpectrum.data

import com.example.dpectrum.api.PasswordCertificationService
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PasswordCertificationRepository @Inject constructor(private val passwordCertificationService: PasswordCertificationService) {
    suspend fun getPasswordCerToken(phone:String):Response<CertificateToken>{
        return passwordCertificationService.certificatePhone(phone)
    }
    suspend fun changePassword(token:String,password:String):Response<PasswordChangeResponse>{
        return passwordCertificationService.changePassword(token,password)
    }
}