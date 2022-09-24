package com.example.challenge7.menu

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.challenge7.history.room.History
import com.example.challenge7.history.room.HistoryDao
import kotlinx.coroutines.launch

class MenuViewModel : ViewModel() {

    private val _histories = MutableLiveData<List<History>>(listOf())
    val histories: LiveData<List<History>> = _histories

//    var userName = blablbalba dari sharedpreference

    fun getHistories(historyDao: HistoryDao) {
        viewModelScope.launch {
            _histories.postValue(historyDao.getHistories("heri"))
//            println("pesan -> ${_histories.value?.size}")
        }
    }

    fun deleteHistories(
        checkedItems: List<History>,
        historyDao: HistoryDao,
        onDeleteFinish: () -> Unit
    ) {
        viewModelScope.launch {
            checkedItems.forEach {
                historyDao.deleteHistory(it)
            }
        }
        onDeleteFinish()
    }

}