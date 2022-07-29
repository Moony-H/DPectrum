package com.example.dpectrum.data

import com.example.dpectrum.api.LoginService
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class LoginServiceRepository @Inject constructor(private val loginService: LoginService) {
    suspend fun postLoginBody(password:String,phone:String):Response<AccessToken>{
        return loginService.login(password,phone)
    }
}