package com.example.challenge7.gameplay

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.challenge7.R
import com.example.challenge7.databinding.ActivityAgainstComBinding
import com.example.challenge7.databinding.ActivityAgainstPlayerBinding
import com.example.challenge7.gameplay.AgainstComActivity.Companion.BATU
import com.example.challenge7.gameplay.AgainstComActivity.Companion.GUNTING
import com.example.challenge7.gameplay.AgainstComActivity.Companion.KERTAS
import com.example.challenge7.gameplay.dialog.ResultDialog
import com.example.challenge7.menu.MenuActivity

class AgainstPlayerActivity : AppCompatActivity() {


    var binding: ActivityAgainstPlayerBinding? = null
    var roundCounter = 0
    var maxRound = 3
    var isPlay = false
    var isPlayer2Turn = false
    var player1Choice = 0
    var comProgress = maxRound
    var playerProgress = maxRound
    var playerName = "Salman"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAgainstPlayerBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        binding?.tvChoice?.text = getString(R.string.choice_silahkan, playerName)

        binding?.ivHome?.setOnClickListener {
            val backToMenu = Intent(this@AgainstPlayerActivity, MenuActivity::class.java)
            startActivity(backToMenu)
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
            if(isPlayer2Turn){
                play(
                    player1Choice,
                    KERTAS
                )
            }
        }

        binding?.ivBatuCOM?.setOnClickListener {
            if(isPlayer2Turn){
                play(
                    player1Choice,
                    BATU
                )
            }
        }

        binding?.ivGuntingCOM?.setOnClickListener {
            if(isPlayer2Turn){
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
        Toast.makeText(this, "Player Menang", Toast.LENGTH_SHORT).show()

    }

    private fun lost() {
        playerProgress -= 1
        binding?.pbPlayer?.progress = playerProgress
        if (roundCounter == maxRound) {
            showDialogResult()
        } else {
            binding?.tvReadyPlayer?.visibility = View.GONE
        }
        Toast.makeText(this, "Player 2 Menang", Toast.LENGTH_SHORT).show()

    }

    private fun showDialogResult() {
        isPlay = true
        binding?.tvChoice?.text = getString(R.string.choice_rematch)
        binding?.ivLastChoiceCOM?.visibility = View.VISIBLE
        binding?.ivLastChoicePlayer?.visibility = View.VISIBLE
        binding?.tvReadyCOM?.visibility = View.VISIBLE
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
}