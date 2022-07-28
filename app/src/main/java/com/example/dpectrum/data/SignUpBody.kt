package com.example.dpectrum.data

import com.google.gson.annotations.SerializedName

data class SignUpBody(
    //@SerializedName("password")
    val password:String,
    //@SerializedName("phoneNumber")
    val phoneNumber:String,
    //@SerializedName("name")
    val name:String,
    //@SerializedName("school")
    val school:String
)