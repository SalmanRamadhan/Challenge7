package com.example.challenge7.gameplay

import android.content.Intent
import android.media.AudioAttributes
import android.media.SoundPool
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import com.example.challenge7.R
import com.example.challenge7.databinding.ActivityAgainstPlayerBinding
import com.example.challenge7.gameplay.AgainstComActivity.Companion.BATU
import com.example.challenge7.gameplay.AgainstComActivity.Companion.GUNTING
import com.example.challenge7.gameplay.AgainstComActivity.Companion.KERTAS
import com.example.challenge7.gameplay.dialog.ResultDialog
import com.example.challenge7.gameplay.viewModel.AgainstPlayerViewModel
import com.example.challenge7.history.room.HistoryDatabase
import com.example.challenge7.helper.SharedPreferences
import com.example.challenge7.menu.MenuActivity
import java.sql.Timestamp

class AgainstPlayerActivity : AppCompatActivity() {

    private val sharedPreferences by lazy { SharedPreferences(this) }
    var binding: ActivityAgainstPlayerBinding? = null
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
    var maxRound = 0
    var isPlay = false
    var isPlayer2Turn = false
    var player1Choice = 0
    var comProgress = 0
    var playerProgress = 0
    var playerName = ""
    var soundWinId = 0
    var soundLoseId = 0
    var soundDrawId = 0
    var soundThemeSongId = 0
    var isAudio = true

    private val viewModel: AgainstPlayerViewModel by viewModels()
    private lateinit var database: HistoryDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAgainstPlayerBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        database = HistoryDatabase.instance(this)

        isAudio = sharedPreferences.music?:false
        binding?.tvChoice?.text = getString(R.string.choice_silahkan, playerName)
        maxRound = sharedPreferences.round ?: 1
        playerName = sharedPreferences.getUser()?.username ?: "Player"
        soundWinId = soundPool.load(this,R.raw.win,1)
        soundLoseId = soundPool.load(this,R.raw.lose,1)
        soundDrawId = soundPool.load(this,R.raw.draw,1)
        soundThemeSongId = soundPool.load(this,R.raw.themesong,1)

        binding?.ivHome?.setOnClickListener {
            val backToMenu = Intent(this@AgainstPlayerActivity, MenuActivity::class.java)
            startActivity(backToMenu)
            finish()
        }

        binding?.pbCOM?.progress = maxRound
        binding?.pbPlayer?.progress = maxRound
        binding?.pbCOM?.max = maxRound
        binding?.pbPlayer?.max = maxRound
        comProgress = maxRound
        playerProgress = maxRound

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
                player1Choice = BATU
                isPlayer2Turn = true
                binding?.tvReadyPlayer?.visibility = View.VISIBLE
            }
        }

        binding?.ivKertas?.setOnClickListener {
            if (!isPlay) {
                player1Choice = KERTAS
                isPlayer2Turn = true
                binding?.tvReadyPlayer?.visibility = View.VISIBLE
            }
        }
        binding?.ivGunting?.setOnClickListener {
            if (!isPlay) {
                player1Choice = GUNTING
                isPlayer2Turn = true
                binding?.tvReadyPlayer?.visibility = View.VISIBLE
            }
        }

        binding?.ivKertasCOM?.setOnClickListener {
            if (isPlayer2Turn) {
                play(
                    player1Choice,
                    KERTAS
                )
            }
        }

        binding?.ivBatuCOM?.setOnClickListener {
            if (isPlayer2Turn) {
                play(
                    player1Choice,
                    BATU
                )
            }
        }

        binding?.ivGuntingCOM?.setOnClickListener {
            if (isPlayer2Turn) {
                play(
                    player1Choice,
                    GUNTING
                )
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
        binding?.tvReadyPlayer?.visibility = View.GONE
        binding?.tvReadyCOM?.visibility = View.GONE
        comProgress = maxRound
        playerProgress = maxRound
        roundCounter = 0
        isPlay = false

    }

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
        } else {
            binding?.tvReadyPlayer?.visibility = View.GONE
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
        } else {
            binding?.tvReadyPlayer?.visibility = View.GONE
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
        } else {
            binding?.tvReadyPlayer?.visibility = View.GONE
        }
        if(isAudio){
            soundPool.play(soundLoseId,1f,1f,1,0,1f)
        }
        Toast.makeText(this, "Player 2 Menang", Toast.LENGTH_SHORT).show()

    }

    private fun showDialogResult() {
        isPlay = true
        binding?.tvChoice?.text = getString(R.string.choice_rematch)
        binding?.ivLastChoiceCOM?.visibility = View.VISIBLE
        binding?.ivLastChoicePlayer?.visibility = View.VISIBLE
        binding?.tvReadyCOM?.visibility = View.VISIBLE

        val timeStamp = Timestamp(System.currentTimeMillis())
        viewModel.saveGameHistory(
            when{
                comProgress == playerProgress -> getString(R.string.result_seri)
                comProgress > playerProgress -> "Kalah"
                comProgress < playerProgress -> "Menang"
                else -> ""
            },
            modePermainan = "Player VS Player",
            timeStamp.time,
            playerName,
            database.getHistoryDao()
        )

        val dialog = ResultDialog(
            when {
                comProgress == playerProgress -> getString(R.string.result_seri)
                comProgress > playerProgress -> "Player 2"
                comProgress < playerProgress -> playerName
                else -> ""
            }, false
        )
        dialog.show(supportFragmentManager, "ResultDialog")
    }
    override fun onDestroy() {
        super.onDestroy()
        soundPool.release()
    }
}