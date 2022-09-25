package com.example.challenge7.setting

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.challenge7.R
import com.example.challenge7.authentication.SignUpFragment
import androidx.appcompat.app.AppCompatActivity
import com.example.challenge7.databinding.ActivitySettingBinding
import com.example.challenge7.helper.SharedPreferences
import com.example.challenge7.menu.MenuActivity

class SettingActivity : AppCompatActivity() {

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

        setOnCLickListener()
        setRound()
        setMusic()

    }

    private fun setMusic() {
        SharedPreferences.let { sp ->
            binding?.sWSound?.isChecked = sp.music == true
            binding?.sWSound?.setOnCheckedChangeListener { _, isChecked ->
                sp.music = isChecked
            }
        }
    }

    private fun setRound() {
        //fill the game round
        SharedPreferences.let {
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
            setMusic()
            if (isRoundFilled()) {
                //save changes
                SharedPreferences.let {
                    it.round = binding?.etGameRound?.text.toString().toInt()
                }
                Toast.makeText(this, "Saved", Toast.LENGTH_LONG).show()
                val i = Intent(this, MenuActivity::class.java)
                startActivity(i)
            } else {
                binding?.etGameRound?.error = "Please fill the round"
            }

        }
    }
}