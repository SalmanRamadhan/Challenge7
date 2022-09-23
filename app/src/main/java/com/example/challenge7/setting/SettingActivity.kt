package com.example.challenge7.setting

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.challenge7.databinding.ActivitySettingBinding
import com.example.challenge7.menu.HomeFragment
import com.example.challenge7.menu.MenuActivity

class SettingActivity : AppCompatActivity() {
    private var binding : ActivitySettingBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding?.root)


        binding?.ivArrowBack?.setOnClickListener {
            onBackPressed()
        }

        binding?.btnHome?.setOnClickListener {
            val home = Intent(this, MenuActivity::class.java)
            startActivity(home)
        }
    }
}