package com.example.dpectrum.data

import com.google.gson.annotations.SerializedName

data class AccessToken(
    @SerializedName("accessToken")
    val token:String,

    @SerializedName("message")
    val message:String
)