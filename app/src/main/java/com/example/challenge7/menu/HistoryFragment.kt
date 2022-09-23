package com.example.challenge7.menu

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.challenge7.databinding.FragmentHistoryBinding
import com.example.challenge7.history.adapter.HistoryAdapter
import com.example.challenge7.history.room.HistoryDatabase
import kotlinx.coroutines.launch


class HistoryFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentHistoryBinding.inflate(layoutInflater)



        return (binding.root)
    }


}