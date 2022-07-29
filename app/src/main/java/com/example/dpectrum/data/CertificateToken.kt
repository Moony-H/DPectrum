package com.example.dpectrum.data

import com.google.gson.annotations.SerializedName

data class CertificateToken(
    @SerializedName("certificateToken")
    val token:String
)