package com.example.dpectrum

import android.app.Application
import android.content.Context
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class DPectrumApplication:Application() {
    init {
        instance = this
    }

    companion object {
        lateinit var instance: DPectrumApplication
        fun getApplicationContext(): Context {
            return instance.applicationContext
        }
    }

}