package com.example.challenge7.menu

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.challenge7.databinding.FragmentHistoryBinding
import com.example.challenge7.helper.SharedPreferences
import com.example.challenge7.history.HistoryDetailActivity
import com.example.challenge7.history.adapter.HistoryAdapter
import com.example.challenge7.history.room.History
import com.example.challenge7.history.room.HistoryDatabase


class HistoryFragment : Fragment() {

    companion object {
        val DETAIL_HISTORY = "data"
    }

    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HistoryViewModel by activityViewModels()
    private lateinit var database: HistoryDatabase
    var name = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        database = HistoryDatabase.instance(requireContext())
        val sharedPreferences = SharedPreferences(requireActivity())
        name = sharedPreferences.getUser()?.username ?: "Username"
        viewModel.getHistories(database.getHistoryDao(), name)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHistoryBinding.inflate(layoutInflater)
        return (binding.root)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.ivDelete.visibility = View.VISIBLE
        binding.cbDeleteAll.visibility = View.INVISIBLE
        binding.clDeleteAll.visibility = View.GONE
        binding.rvHistory.visibility = View.VISIBLE




        viewModel.histories.observe(viewLifecycleOwner) {

            binding.ivDelete.setOnClickListener {
                historyAvailable()
            }
            binding.tvCancel.setOnClickListener {
                historyEmpty()
            }


            binding.rvHistory.apply {
                val historyAdapter = HistoryAdapter(
                    onHistoryCheckedListener = { _, itemCheckedHistorySum ->
                        binding.cbDeleteAll.isChecked = itemCheckedHistorySum == it.size
                        if (itemCheckedHistorySum != 0) {
                            binding.tvDelete.visibility = View.VISIBLE
                        } else if (itemCheckedHistorySum == 0) {
                            binding.cbDeleteAll.isChecked = false
                        } else {
                            binding.tvDelete.visibility = View.GONE
                        }
                    }, onClickListener = { history ->
                        val intent = Intent(requireActivity(), HistoryDetailActivity::class.java)
                        intent.putExtra("data", history )
                        startActivity(intent)
                    }
                )
                binding.cbDeleteAll.setOnCheckedChangeListener { _, checked ->
                    historyAdapter.setAllChecked(checked)
                    binding.tvDelete.visibility = if (checked) {
                        View.VISIBLE
                    } else
                        View.GONE

                }
                historyAdapter.setData(it)

                layoutManager = LinearLayoutManager(context)
                adapter = historyAdapter
//                println("pesan -> ${it.size}")

                binding.tvDelete.setOnClickListener {
                    val checkedItems = historyAdapter.getAllChecked()
                    viewModel.deleteHistories(checkedItems, database.getHistoryDao()) {
                        viewModel.getHistories(database.getHistoryDao(), name)
                    }
                }
            }
        }


    }

    fun historyAvailable() {
        binding.apply {
            rvHistory.visibility = View.VISIBLE
            ivDelete.visibility = View.VISIBLE
            tvCancel.visibility = View.VISIBLE
            clDeleteAll.visibility = View.VISIBLE
            cbDeleteAll.visibility = View.VISIBLE
        }
    }

    fun historyEmpty() {
        binding.apply {
            clDeleteAll.visibility = View.GONE
            rvHistory.visibility = View.GONE
            ivDelete.visibility = View.GONE
            tvCancel.visibility = View.GONE
            cbDeleteAll.visibility = View.GONE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        viewModel.histories.removeObservers(viewLifecycleOwner)
    }

}