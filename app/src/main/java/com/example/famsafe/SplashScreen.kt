package com.example.famsafe

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity


class SplashScreen :AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        SharedPreference.init(this)
        val isUSerLoggedIn=SharedPreference.getBoolean("isUSerLoggedIn")
        if(isUSerLoggedIn){
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
        else{
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

    }
}