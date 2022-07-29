package com.example.dpectrum.api

import com.example.dpectrum.data.TutorContent
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface ContentService {
    @GET("content")
    suspend fun getContent(
        @Header("Authorization")
        token:String,
        @Query("offset")
        offset:String,
        @Query("limit")
        limit:String,
        @Query("department")
        department:String
    ):Response<List<TutorContent>>
}