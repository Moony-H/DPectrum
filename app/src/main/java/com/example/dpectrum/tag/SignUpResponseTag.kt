package com.example.dpectrum.tag

enum class SignUpResponseTag(val num:Int) {
    RESPONSE_SUCCESS(200),
    RESPONSE_DUPLICATED(209),
    RESPONSE_NO_PARAMS(400)
}