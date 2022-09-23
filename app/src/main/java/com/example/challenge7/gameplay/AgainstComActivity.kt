package com.example.challenge7.gameplay


import android.os.Bundle

import androidx.appcompat.app.AppCompatActivity
import com.example.challenge7.databinding.ActivityAgainstComBinding
import com.example.challenge7.history.room.History
import com.example.challenge7.history.room.HistoryDatabase
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.sql.Time
import java.time.LocalDate
import java.time.LocalTime
import java.util.*

class AgainstComActivity : AppCompatActivity() {
    var binding: ActivityAgainstComBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAgainstComBinding.inflate(layoutInflater)
        setContentView(binding?.root)

    }

//    private fun addHistory(){
//        val hasil = binding.tvResult.text
//        val date = ""
//        val hour = ""
//        val userName = ""
//
//        GlobalScope.launch {
//            val histories = History(hasilPermainan = hasil, date = date, time = hour, userName = userName)
//            HistoryDatabase(this@AgainstComActivity).getHistoryDao().insertHistory(histories)
//            finish()
//        }
//    }

}