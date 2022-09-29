package com.example.challenge7.history

import android.annotation.SuppressLint
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.challenge7.R
import com.example.challenge7.databinding.ActivityHistoryDetailBinding
import com.example.challenge7.history.room.History
import com.example.challenge7.menu.HistoryFragment
import com.example.challenge7.menu.HistoryFragment.Companion.DETAIL_HISTORY
import java.text.SimpleDateFormat
import java.util.*

class HistoryDetailActivity : AppCompatActivity() {
    private lateinit var binding : ActivityHistoryDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val historyDetail = if (Build.VERSION.SDK_INT >= 33) {
            intent.getParcelableExtra(DETAIL_HISTORY, History::class.java)
        } else {
            intent.getParcelableExtra<History>(DETAIL_HISTORY)
        }

        val hasilPermainan = binding.tvHasilPermainan
        val modePermainan = binding.tvHasilPermainan2
        val date = binding.tvDate
        val hour = binding.tvHour
        val userName = binding.tvUserName
        val text = "${historyDetail?.userName} ${historyDetail?.hasilPermainan}"


        val sdfDate = SimpleDateFormat("EEEE, dd MMM yyyy", Locale("id", "ID"))
        val sdfTime = SimpleDateFormat("hh:mm:ss", Locale("id", "ID"))

        hasilPermainan.text = historyDetail?.hasilPermainan
        modePermainan.text = historyDetail?.modePermainan
        date.text = sdfDate.format(historyDetail?.timeStamp)
        hour.text = sdfTime.format(historyDetail?.timeStamp)
        userName.text = text
        binding.ivBack.setOnClickListener {
            finish()
        }

    }
}