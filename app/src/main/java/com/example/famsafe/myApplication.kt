package com.example.famsafe

import android.app.Application

class myApplication:Application() {
    override fun onCreate() {
        super.onCreate()
        SharedPreference.init(this)
    }
}