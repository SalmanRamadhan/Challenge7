package com.example.challenge7.gameplay.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.challenge7.history.room.History
import com.example.challenge7.history.room.HistoryDao
import kotlinx.coroutines.launch

class AgainstCpuViewModel : ViewModel() {


    fun saveGameHistory(
        hasilPermainan: String,
        timeStamp: Long,
        userName: String,
        historyDao: HistoryDao
    ) {
        viewModelScope.launch {
            historyDao.insertHistory(
                History(
                    id = null,
                    hasilPermainan = hasilPermainan,
                    time = "",
                    date = "",
                    userName = userName,
                    timeStamp = timeStamp
                )
            )
        }
    }
}