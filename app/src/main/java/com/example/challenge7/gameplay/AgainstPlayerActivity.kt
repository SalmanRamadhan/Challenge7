package com.example.challenge7.gameplay

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.challenge7.R
import com.example.challenge7.databinding.ActivityAgainstComBinding
import com.example.challenge7.gameplay.dialog.ResultDialog
import com.example.challenge7.menu.MenuActivity

class AgainstPlayerActivity : AppCompatActivity() {



    var binding: ActivityAgainstComBinding? = null
    var roundCounter = 0
    var maxRound = 3
    var isPlay = false
    var comProgress = maxRound
    var playerProgress = maxRound
    var playerName = "Salman"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAgainstComBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        binding?.tvChoice?.text = getString(R.string.choice_silahkan,playerName)

        binding?.ivHome?.setOnClickListener {
            val backToMenu = Intent(this@AgainstPlayerActivity, MenuActivity::class.java)
            startActivity(backToMenu)
        }

        binding?.pbCOM?.progress = maxRound
        binding?.pbPlayer?.progress = maxRound
        binding?.pbCOM?.max = maxRound
        binding?.pbPlayer?.max = maxRound

        binding?.tvChoice?.setOnClickListener {
            if(binding?.tvChoice?.text == getString(R.string.choice_rematch)){
                reset()
            }
        }

        binding?.ivRestart?.setOnClickListener {
            reset()
        }

        binding?.ivBatu?.setOnClickListener {
            if (!isPlay) {
                play(AgainstComActivity.BATU, generateRandomChoice())
            }
        }

        binding?.ivKertas?.setOnClickListener {
            if (!isPlay) {
                play(AgainstComActivity.KERTAS, generateRandomChoice())
            }
        }
        binding?.ivGunting?.setOnClickListener {
            if (!isPlay) {
                play(AgainstComActivity.GUNTING, generateRandomChoice())
            }
        }
    }

    fun reset() {

        binding?.tvChoice?.text = getString(R.string.choice_silahkan,playerName)
        binding?.pbCOM?.progress = maxRound
        binding?.pbPlayer?.progress = maxRound
        binding?.pbCOM?.max = maxRound
        binding?.pbPlayer?.max = maxRound
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
            p1 == AgainstComActivity.BATU && p2 == AgainstComActivity.GUNTING -> {
                won()
            }
            p1 == AgainstComActivity.KERTAS && p2 == AgainstComActivity.BATU -> {
                won()
            }
            p1 == AgainstComActivity.GUNTING && p2 == AgainstComActivity.KERTAS -> {
                won()
            }
            else -> lost()
        }

    }

    private fun draw() {

        if (roundCounter == maxRound) {
            showDialogResult()
        }
        Toast.makeText(this, "Draw", Toast.LENGTH_SHORT).show()

    }

    private fun won() {

        comProgress -= 1
        binding?.pbCOM?.progress = comProgress
        if (roundCounter == maxRound) {
            showDialogResult()
        }
        Toast.makeText(this, "Player Menang", Toast.LENGTH_SHORT).show()

    }

    private fun lost() {
        playerProgress -= 1
        binding?.pbPlayer?.progress = playerProgress
        if (roundCounter == maxRound) {
            showDialogResult()
        }
        Toast.makeText(this, "COM Menang", Toast.LENGTH_SHORT).show()

    }

    private fun showDialogResult() {
        isPlay = true
        binding?.tvChoice?.text = getString(R.string.choice_rematch)
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