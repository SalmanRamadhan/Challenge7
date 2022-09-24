package com.example.challenge7.setting

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import com.example.challenge7.R
import com.example.challenge7.authentication.SignUpFragment
import com.example.challenge7.databinding.ActivitySettingBinding
import com.example.challenge7.menu.MenuActivity

class SettingActivity : AppCompatActivity() {

    companion object {
        var round: Int = 1
    }

    var binding: ActivitySettingBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        binding?.ivArrowBack?.setOnClickListener {
            val i = Intent(this, MenuActivity::class.java)
            startActivity(i)
        }

        binding?.btnHome?.setOnClickListener {
            //back to HomeFragment
            val i = Intent(this, MenuActivity::class.java)
            startActivity(i)
        }

        binding?.btnSave?.setOnClickListener {
            setRound()
            setMusic()
        }

    }

    private fun setMusic() {
        val music = binding?.sWSound?.isChecked
        if (music == true) {
            //play music
        } else {
            //stop music
        }
    }

    private fun setRound() {
        //fill the game round
        round = binding?.etGameRound?.text.toString().toInt()

        if (round > 5) {
            binding?.etGameRound?.error = "Maksimal 5 round"
            return
        }
    }
}