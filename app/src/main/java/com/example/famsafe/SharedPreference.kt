package com.example.famsafe

import android.content.Context
import android.content.SharedPreferences
import android.icu.lang.UProperty.NAME
import android.media.MediaCodec.MetricsConstants.MODE

object SharedPreference {
    private const val NAME = "mySharedPreference"
    private const val MODE = Context.MODE_PRIVATE
    private lateinit var preferences: SharedPreferences
    fun init(context: Context) {
        preferences = context.getSharedPreferences(NAME, MODE)
    }
    fun putBoolean(key:String,value:Boolean){
        preferences.edit().putBoolean(key,value).commit()
    }
    fun getBoolean(key:String):Boolean{
        return preferences.getBoolean(key,false)
    }
}