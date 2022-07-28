package com.example.dpectrum.di

import com.example.dpectrum.api.SignUpService
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class RetrofitModule {

    //Hilt 에 Interface 타입의 객체를 생성하는 방법을 알림.

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {
        val builder = OkHttpClient.Builder()

        return Retrofit.Builder()
            .baseUrl("https://test.dpectrum.app/").addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .client(builder.build())
            .build()
    }

    @Singleton
    @Provides
    fun provideSignUpService(retrofit: Retrofit): SignUpService {
        return retrofit.create(SignUpService::class.java)
    }
}