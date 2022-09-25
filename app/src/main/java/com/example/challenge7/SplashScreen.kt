package com.example.challenge7

import android.annotation.SuppressLint
import android.content.Intent
import android.media.AudioAttributes
import android.media.SoundPool
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.Window
import android.view.WindowManager
import com.example.challenge7.databinding.ActivitySplashScreenBinding
import com.example.challenge7.helper.SharedPreferences
import com.example.challenge7.landingpage.LandingPage
import com.example.challenge7.menu.MenuActivity

@SuppressLint("CustomSplashScreen")
class SplashScreen : AppCompatActivity() {

    var binding : ActivitySplashScreenBinding? = null
    var isAudio = false
    var soundId = 0
    val soundPool : SoundPool by lazy {
        val audioAttributes = AudioAttributes.Builder()
            .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
            .setUsage(AudioAttributes.USAGE_MEDIA)
            .build()

        SoundPool.Builder()
            .setAudioAttributes(audioAttributes)
            .build()
    }

    private val sharedPreferences by lazy { SharedPreferences(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        window.statusBarColor = resources.getColor(R.color.maincolor)

        soundId = soundPool.load(this,R.raw.splashscreen,1)

        if(isAudio){
            soundPool.play(soundId,1f,1f,1,0,1f)
        }
        Handler(Looper.getMainLooper()).postDelayed({
            if(sharedPreferences.getStatusLogin()){
                startActivity(Intent(this,MenuActivity::class.java))
            } else {
                startActivity(Intent(this, LandingPage::class.java))
            }
            finish()
        }, 3000)

    }
}