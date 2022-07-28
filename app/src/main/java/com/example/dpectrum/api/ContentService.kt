package com.example.dpectrum.api

import retrofit2.http.GET

interface ContentService {
    @GET("/content?offset&limit&department=1")
    suspend fun getContent()
}