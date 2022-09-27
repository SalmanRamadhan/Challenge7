package com.example.challenge7.menu

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.bumptech.glide.Glide
import com.example.challenge7.R
import com.example.challenge7.authentication.LoginActivity
import com.example.challenge7.databinding.FragmentProfileBinding
import com.example.challenge7.helper.SharedPreferences
import com.example.challenge7.menu.presenter.AddPresenterImpl
import com.example.challenge7.model.GetUserProfileResponse
import com.example.challenge7.model.UserData
import com.google.gson.Gson

interface AddView {

    fun showLoading()

    fun hideLoading()

    fun addResultSuccess(user: String)

    fun addResultFailed(message: String)
}

class ProfileFragment : Fragment(), AddView {

    private val sharedPreferences by lazy {
        SharedPreferences(requireActivity())
    }

    val progressDialog: ProgressDialog by lazy {
        ProgressDialog(context)
    }

    var addView: AddView? = null
    private val presenter: AddPresenterImpl by lazy {

        AddPresenterImpl(addView)
    }
    var binding: FragmentProfileBinding? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = FragmentProfileBinding.inflate(layoutInflater)
        var userToShow: UserData = sharedPreferences.getUser()!!

        binding?.tvLogout?.setOnClickListener {

            with(progressDialog) {

                setMessage("Loading...")
                setCancelable(false)
                show()
            }

            sharedPreferences.setStatusLogin(false)
            val intent = Intent(activity, LoginActivity::class.java)
            startActivity(intent)
            activity?.finish()
        }

        fetchData(userToShow.token)

        return binding?.root
    }

    fun fetchData(token: String) {
        presenter.fetchInputPosting(token)
    }

    override fun showLoading() {
        binding?.civProfile?.visibility = View.INVISIBLE
    }

    override fun hideLoading() {
        binding?.civProfile?.visibility = View.VISIBLE
    }

    override fun addResultSuccess(user: String) {
        val data = Gson().fromJson<GetUserProfileResponse>(user, GetUserProfileResponse::class.java)
        binding?.etEmail?.setText(data.data.email)
        binding?.etUserName?.setText(data.data.username)
        Glide.with(requireActivity())
            .load(data.data.photo)
            .circleCrop()
            .placeholder(R.drawable.ic_launcher_background)
            .error(R.drawable.ic_launcher_background)
            .into(binding?.civProfile!!)
        Log.d("Tess", "${data.data.photo}")
    }

    override fun addResultFailed(message: String) {
        Toast.makeText(requireContext(), "$message", Toast.LENGTH_SHORT).show()
    }
}