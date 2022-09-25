package com.example.challenge7.gameplay.dialog

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.challenge7.R
import com.example.challenge7.databinding.LayoutDialogResultBinding
import com.example.challenge7.gameplay.AgainstComActivity
import com.example.challenge7.gameplay.AgainstPlayerActivity

class ResultDialog(name: String, isCOM: Boolean) : DialogFragment() {

    var binding: LayoutDialogResultBinding? = null
    var nameResult = name
    var _isCOM = isCOM

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = LayoutDialogResultBinding.inflate(inflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val width = ViewGroup.LayoutParams.MATCH_PARENT
        val height = ViewGroup.LayoutParams.WRAP_CONTENT
        dialog?.window?.setLayout(width, height)

        if (nameResult == getString(R.string.result_seri)) binding?.tvMenang?.visibility =
            View.INVISIBLE else binding?.tvMenang?.visibility = View.VISIBLE

        binding?.tvName?.text = nameResult

        binding?.BackToMenu?.setOnClickListener {
            activity?.finish()
        }

        binding?.btnPlayAgain?.setOnClickListener {

            if(_isCOM){
                (activity as AgainstComActivity).reset()
            } else {
                (activity as AgainstPlayerActivity).reset()
            }
            dialog?.dismiss()

        }


    }
}