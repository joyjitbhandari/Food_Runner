package com.joyjit.foodrunner.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.joyjit.foodrunner.R

class SplashScreen : AppCompatActivity() {
    val splash_screen_time_out = 3000L
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splashscreen)

        Handler().postDelayed({
            startActivity(Intent(this, Login::class.java))
            finish()
        },splash_screen_time_out)
    }
}


