package com.example.challenge7.history.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.challenge7.R
import com.example.challenge7.history.room.History
import java.text.SimpleDateFormat
import java.util.*

class HistoryAdapter(
    private val onHistoryCheckedListener: (Boolean, Int) -> Unit,
    private val onClickListener: (History) -> Unit
) :
    RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {
    private var listHistory = mutableListOf<HistoryCheck>()
    private var showCheckBox: Boolean = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_history, parent, false)
        return ViewHolder(itemView)
    }
    
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = listHistory[position]

        val date = Date(data.history.timeStamp)
        val sdfDate = SimpleDateFormat("EEEE, dd MMM yyyy", Locale("id", "ID"))
        val sdfTime = SimpleDateFormat("hh:mm:ss", Locale("id", "ID"))


        holder.cbCheck.visibility = if (showCheckBox) View.VISIBLE else View.GONE

        holder.tvHasilPermainan.text = data.history.hasilPermainan
        holder.tvModePermainan.text = data.history.modePermainan
        holder.tvDate.text = sdfDate.format(date)
        holder.tvHour.text = sdfTime.format(date)
        holder.cbCheck.isChecked = data.checked
        holder.cbCheck.setOnCheckedChangeListener { _, checked ->
            data.checked = checked
//            notifyItemChanged(position)
            val itemCheckedSum = listHistory.filter { it.checked }.size
            onHistoryCheckedListener(checked, itemCheckedSum)
        }

        holder.itemView.setOnClickListener {
//            Toast.makeText(it.context, data.history.hasilPermainan, Toast.LENGTH_SHORT).show()
            onClickListener(data.history)
        }
    }

    override fun getItemCount() = listHistory.size


    fun setData(data: List<History>) {
        listHistory.apply {
            clear()
            val transformArray = data.map {
                HistoryCheck(false, it)
            }
            addAll(transformArray)
        }
    }

    fun setAllChecked(checked: Boolean) {
        listHistory.forEach {
            it.checked = checked
        }
        notifyItemRangeChanged(0, listHistory.size)
    }

    fun getAllChecked(): List<History> {
        return listHistory.filter {
            it.checked
        }.map {
            it.history
        }
    }

    fun showHideCheckBox(show: Boolean){
        showCheckBox = show
        notifyItemRangeChanged(0, listHistory.size)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvHasilPermainan: TextView = itemView.findViewById(R.id.tvHasilPermainan)
        val tvModePermainan: TextView = itemView.findViewById(R.id.tvHasilPermainan2)
        val tvDate: TextView = itemView.findViewById(R.id.tvDate)
        val tvHour: TextView = itemView.findViewById(R.id.tvHour)
        val cbCheck: CheckBox = itemView.findViewById(R.id.cbDelete)
    }

    data class HistoryCheck(
        var checked: Boolean,
        var history: History
    )
}