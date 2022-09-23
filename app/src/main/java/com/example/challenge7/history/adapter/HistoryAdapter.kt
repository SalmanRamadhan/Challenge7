package com.example.challenge7.history.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.challenge7.R
import com.example.challenge7.history.room.History

class HistoryAdapter:RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {
    private var listHistory = mutableListOf<History>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_history, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val history = listHistory[position]
        holder.tvHasilPermainan.text = history.hasilPermainan
        holder.tvDate.text = history.date.toString()
        holder.tvHour.text = history.time.toString()
    }

    override fun getItemCount() = listHistory.size

    fun setData(data: List<History>){
        listHistory.apply {
            clear()
            addAll(data)
        }
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val tvHasilPermainan: TextView = itemView.findViewById(R.id.tvHasilPermainan)
        val tvDate: TextView = itemView.findViewById(R.id.tvDate)
        val tvHour: TextView = itemView.findViewById(R.id.tvHour)
    }


}