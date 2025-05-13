package com.a2004256_ahmedmohamed.newsapp.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.a2004256_ahmedmohamed.newsapp.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        runBlocking {
            delay(2000)
            startActivity(Intent(this@SplashActivity, OnBoardingActivity::class.java))
        }

    }
}