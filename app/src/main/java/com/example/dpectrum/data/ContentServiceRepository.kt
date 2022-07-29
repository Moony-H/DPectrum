package com.example.dpectrum.data

import com.example.dpectrum.api.ContentService
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ContentServiceRepository @Inject constructor(private val contentService: ContentService){
    suspend fun getContents(token:String,department:String):Response<List<TutorContent>>{
        return contentService.getContent(token,"0","10",department)
    }
}