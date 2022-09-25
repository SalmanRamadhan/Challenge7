package com.example.challenge7.setting

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.challenge7.databinding.ActivitySettingBinding
import com.example.challenge7.helper.SharedPreferences
import com.example.challenge7.menu.MenuActivity

class SettingActivity : AppCompatActivity() {

    companion object {
        var round: Int = 1
    }

    private val SharedPreferences by lazy { SharedPreferences(this) }

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

        setMusic()
        setOnCLickListener()
        setRound()

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
        SharedPreferences?.let {
            binding?.etGameRound?.setText(it.round.toString())
        }
    }

    private fun isRoundFilled(): Boolean {
        val round = binding?.etGameRound?.text.toString()
        var isRoundFilled = true

        if (round.isEmpty()) {
            isRoundFilled = false
        }
        return isRoundFilled
    }

    private fun setOnCLickListener() {
        binding?.btnSave?.setOnClickListener {
            if (isRoundFilled()) {
                //save the round
                SharedPreferences.let {
                    it.round = binding?.etGameRound?.text.toString().toInt()
                }
                val i = Intent(this, MenuActivity::class.java)
                startActivity(i)
            } else {
                binding?.etGameRound?.error = "Please fill the round"
            }
        }
    }
}