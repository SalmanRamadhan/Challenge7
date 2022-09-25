package com.example.challenge7.gameplay

import android.content.ContentValues.TAG
import android.content.Intent
import android.media.AudioAttributes
import android.media.SoundPool
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast

import com.example.challenge7.R
import com.example.challenge7.authentication.LoginActivity
import com.example.challenge7.databinding.ActivityAgainstComBinding
import com.example.challenge7.gameplay.AgainstComActivity.Companion.BATU
import com.example.challenge7.gameplay.AgainstComActivity.Companion.GUNTING
import com.example.challenge7.gameplay.AgainstComActivity.Companion.KERTAS
import com.example.challenge7.gameplay.dialog.ResultDialog
import com.example.challenge7.helper.SharedPreferences
import com.example.challenge7.menu.MenuActivity
import com.example.challenge7.setting.SettingActivity.Companion.round
import kotlin.math.max

class AgainstComActivity : AppCompatActivity() {

    companion object {

        const val BATU = 1
        const val KERTAS = 2
        const val GUNTING = 3

    }

    private val sharedPreferences by lazy { SharedPreferences(this) }
    var binding: ActivityAgainstComBinding? = null
    val soundPool : SoundPool by lazy {
        val audioAttributes = AudioAttributes.Builder()
            .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
            .setUsage(AudioAttributes.USAGE_MEDIA)
            .build()

        SoundPool.Builder()
            .setAudioAttributes(audioAttributes)
            .build()
    }

    var roundCounter = 0
    var maxRound = 3
    var isPlay = false
    var comProgress = maxRound
    var playerProgress = maxRound
    var playerName = "Salman"
    var soundWinId = 0
    var soundLoseId = 0
    var soundDrawId = 0
    var soundThemeSongId = 0
    var isAudio = true


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAgainstComBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        maxRound = sharedPreferences.round ?: 1
        playerName = sharedPreferences.getUser()?.username ?: "Player"
        binding?.tvChoice?.text = getString(R.string.choice_silahkan, playerName)
        soundWinId = soundPool.load(this,R.raw.win,1)
        soundLoseId = soundPool.load(this,R.raw.lose,1)
        soundDrawId = soundPool.load(this,R.raw.draw,1)
        soundThemeSongId = soundPool.load(this,R.raw.themesong,1)

        if(isAudio){
            soundPool.play(soundThemeSongId,1f,1f,1,-1,1f)
        }

        binding?.ivHome?.setOnClickListener {
            val backToMenu = Intent(this@AgainstComActivity, MenuActivity::class.java)
            startActivity(backToMenu)
            finish()
        }

        binding?.pbCOM?.progress = maxRound
        binding?.pbPlayer?.progress = maxRound
        binding?.pbCOM?.max = maxRound
        binding?.pbPlayer?.max = maxRound

        binding?.tvChoice?.setOnClickListener {
            if (binding?.tvChoice?.text == getString(R.string.choice_rematch)) {
                reset()
            }
        }

        binding?.ivRestart?.setOnClickListener {
            reset()
        }

        binding?.ivBatu?.setOnClickListener {
            if (!isPlay) {
                play(BATU, generateRandomChoice())
            }
        }

        binding?.ivKertas?.setOnClickListener {
            if (!isPlay) {
                play(KERTAS, generateRandomChoice())
            }
        }
        binding?.ivGunting?.setOnClickListener {
            if (!isPlay) {
                play(GUNTING, generateRandomChoice())
            }
        }

    }

    fun reset() {

        binding?.tvChoice?.text = getString(R.string.choice_silahkan, playerName)
        binding?.pbCOM?.progress = maxRound
        binding?.pbPlayer?.progress = maxRound
        binding?.pbCOM?.max = maxRound
        binding?.pbPlayer?.max = maxRound
        binding?.ivLastChoiceCOM?.visibility = View.GONE
        binding?.ivLastChoicePlayer?.visibility = View.GONE
        comProgress = maxRound
        playerProgress = maxRound
        roundCounter = 0
        isPlay = false

    }

    private fun generateRandomChoice(): Int = (1 until 4).random()

    private fun play(p1: Int, p2: Int) {

        roundCounter += 1

        when {
            p1 == p2 -> {
                draw()
            }
            p1 == BATU && p2 == GUNTING -> {
                won()
            }
            p1 == KERTAS && p2 == BATU -> {
                won()
            }
            p1 == GUNTING && p2 == KERTAS -> {
                won()
            }
            else -> lost()
        }

        if (roundCounter == maxRound) {
            when {
                p1 == BATU -> {
                    binding?.ivLastChoicePlayer?.setImageResource(R.drawable.ic_batu)
                }
                p1 == KERTAS -> {
                    binding?.ivLastChoicePlayer?.setImageResource(R.drawable.ic_kertas)
                }
                p1 == GUNTING -> {
                    binding?.ivLastChoicePlayer?.setImageResource(R.drawable.ic_gunting)
                }
                p2 == BATU -> {
                    binding?.ivLastChoiceCOM?.setImageResource(R.drawable.ic_batu)
                }
                p2 == KERTAS -> {
                    binding?.ivLastChoiceCOM?.setImageResource(R.drawable.ic_kertas)
                }
                p2 == GUNTING -> {
                    binding?.ivLastChoiceCOM?.setImageResource(R.drawable.ic_gunting)
                }
            }
        }

    }

    private fun draw() {

        if (roundCounter == maxRound) {
            showDialogResult()
        }
        if(isAudio){
            soundPool.play(soundDrawId,1f,1f,1,0,1f)
        }
        Toast.makeText(this, "Draw", Toast.LENGTH_SHORT).show()

    }

    private fun won() {

        comProgress -= 1
        binding?.pbCOM?.progress = comProgress
        if (roundCounter == maxRound) {
            showDialogResult()
        }
        if(isAudio){
            soundPool.play(soundWinId,1f,1f,1,0,1f)
        }
        Toast.makeText(this, "$playerName Menang", Toast.LENGTH_SHORT).show()

    }

    private fun lost() {
        playerProgress -= 1
        binding?.pbPlayer?.progress = playerProgress
        if (roundCounter == maxRound) {
            showDialogResult()
        }
        if(isAudio){
            soundPool.play(soundLoseId,1f,1f,1,0,1f)
        }
        Toast.makeText(this, "COM Menang", Toast.LENGTH_SHORT).show()

    }

    private fun showDialogResult() {
        binding?.tvChoice?.text = getString(R.string.choice_rematch)
        binding?.ivLastChoiceCOM?.visibility = View.VISIBLE
        binding?.ivLastChoicePlayer?.visibility = View.VISIBLE

        isPlay = true
        val dialog = ResultDialog(
            when {
                comProgress == playerProgress -> getString(R.string.result_seri)
                comProgress > playerProgress -> getString(R.string.result_com)
                comProgress < playerProgress -> playerName
                else -> ""
            }
        )
        dialog.show(supportFragmentManager, "ResultDialog")
    }

    override fun onDestroy() {
        super.onDestroy()
        soundPool.release()
    }


}