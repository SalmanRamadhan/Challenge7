package com.example.challenge7.gameplay

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels

import com.example.challenge7.R
import com.example.challenge7.databinding.ActivityAgainstComBinding
import com.example.challenge7.gameplay.dialog.ResultDialog
import com.example.challenge7.gameplay.viewModel.AgainstCpuViewModel
import com.example.challenge7.history.room.HistoryDatabase
import com.example.challenge7.menu.MenuActivity
import java.sql.Timestamp

class AgainstComActivity : AppCompatActivity() {

    companion object {

        const val BATU = 1
        const val KERTAS = 2
        const val GUNTING = 3

    }

    var binding: ActivityAgainstComBinding? = null
    var roundCounter = 0
    var maxRound = 3
    var isPlay = false
    var comProgress = maxRound
    var playerProgress = maxRound
    var playerName = "Salman"

    private val viewModel: AgainstCpuViewModel by viewModels()
    private lateinit var database:HistoryDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAgainstComBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        database = HistoryDatabase.instance(this)


        binding?.tvChoice?.text = getString(R.string.choice_silahkan,playerName)

        binding?.ivHome?.setOnClickListener {
            val backToMenu = Intent(this@AgainstComActivity, MenuActivity::class.java)
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

    }

    private fun draw() {

        if (roundCounter == maxRound) {
            val timeStamp = Timestamp(System.currentTimeMillis())
            viewModel.saveGameHistory("Draw",timeStamp.time, "Salman", database.getHistoryDao())
            showDialogResult()
        }
        Toast.makeText(this, "Draw", Toast.LENGTH_SHORT).show()

    }

    private fun won() {

        comProgress -= 1
        binding?.pbCOM?.progress = comProgress
        if (roundCounter == maxRound) {
            val timeStamp = Timestamp(System.currentTimeMillis())
            viewModel.saveGameHistory("Win", timeStamp.time, "Salman", database.getHistoryDao())
            showDialogResult()
        }
        Toast.makeText(this, "Player Menang", Toast.LENGTH_SHORT).show()

    }

    private fun lost() {
        playerProgress -= 1
        binding?.pbPlayer?.progress = playerProgress
        if (roundCounter == maxRound) {
            val timeStamp = Timestamp(System.currentTimeMillis())
            viewModel.saveGameHistory("Lose", timeStamp.time, "Salman", database.getHistoryDao())
            showDialogResult()
        }
        Toast.makeText(this, "COM Menang", Toast.LENGTH_SHORT).show()

    }

    private fun showDialogResult() {
        binding?.tvChoice?.text = getString(R.string.choice_rematch)

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


}