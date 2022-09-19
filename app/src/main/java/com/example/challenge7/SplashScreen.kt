package com.example.challenge7

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.Window
import android.view.WindowManager
import com.example.challenge7.databinding.ActivitySplashScreenBinding
import com.example.challenge7.landingpage.LandingPage

@SuppressLint("CustomSplashScreen")
class SplashScreen : AppCompatActivity() {
    var binding : ActivitySplashScreenBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        window.statusBarColor = resources.getColor(R.color.maincolor)


        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, LandingPage::class.java))
            finish()
        }, 3000)

    }
}