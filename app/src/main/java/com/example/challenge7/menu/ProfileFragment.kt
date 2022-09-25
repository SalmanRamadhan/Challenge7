package com.example.challenge7.menu

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import com.bumptech.glide.Glide
import com.example.challenge7.R
import com.example.challenge7.authentication.LoginActivity
import com.example.challenge7.databinding.FragmentLoginBinding
import com.example.challenge7.databinding.FragmentProfileBinding
import com.example.challenge7.helper.SharedPreferences
import com.example.challenge7.model.UserData


class ProfileFragment : Fragment() {

    private val sharedPreferences by lazy {
        SharedPreferences(requireActivity())
    }
    var binding: FragmentProfileBinding? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = FragmentProfileBinding.inflate(layoutInflater)
        var userToShow : UserData = sharedPreferences.getUser()!!

        binding?.etUserName?.setText(userToShow.username)
        binding?.etEmail?.setText(userToShow.email)
        binding?.tvLogout?.setOnClickListener {
            sharedPreferences.setStatusLogin(false)

            val intent = Intent(activity,LoginActivity::class.java)
            startActivity(intent)
            activity?.finish()
        }
        binding?.civProfile?.setOnClickListener {
            val intentPickImage = Intent(Intent.ACTION_PICK)
            intentPickImage.type = "image/*"
            startActivity(intentPickImage)
        }
        var resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ result ->
            if (result.resultCode == Activity.RESULT_OK) {
                // There are no request codes
                val data: Intent? = result.data
            }
        }


        val imageView = binding?.civProfile

        Glide.with(this@ProfileFragment)
            .load(userToShow.photo)
            .placeholder(R.drawable.ic_image_profile)
            .error(R.drawable.ic_image_profile)
            .circleCrop()
            .into(imageView!!)

        return binding?.root
    }


}