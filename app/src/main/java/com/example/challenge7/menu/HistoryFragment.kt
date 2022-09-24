package com.example.challenge7.menu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.challenge7.databinding.FragmentHistoryBinding
import com.example.challenge7.history.adapter.HistoryAdapter
import com.example.challenge7.history.room.HistoryDatabase


class HistoryFragment : Fragment() {

    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MenuViewModel by activityViewModels()
    private lateinit var database: HistoryDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        database = HistoryDatabase.instance(requireContext())
        viewModel.getHistories(database.getHistoryDao())
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

        viewModel.histories.observe(viewLifecycleOwner) {
            binding.rvHistory.apply {
                val historyAdapter = HistoryAdapter { checked, itemCheckedHistorySum ->
                    binding.cbDelete.isChecked = itemCheckedHistorySum == it.size
                    if (itemCheckedHistorySum > 0) {
                        binding.tvDelete.visibility = View.VISIBLE
                    } else
                        binding.tvDelete.visibility = View.GONE

                }
                binding.cbDelete.setOnCheckedChangeListener { _, checked ->
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
                        viewModel.getHistories(database.getHistoryDao())
                    }
                }
            }
        }


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        viewModel.histories.removeObservers(viewLifecycleOwner)
    }

}