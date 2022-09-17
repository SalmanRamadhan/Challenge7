package com.example.challenge7.landingpage

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.challenge7.authentication.LoginActivity
import com.example.challenge7.authentication.LoginFragment
import com.example.challenge7.authentication.SignUpFragment
import com.example.challenge7.databinding.FragmentLanding3Binding

class LandingFragment3 : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentLanding3Binding.inflate(layoutInflater)
        binding.apply {
            btnLogin.setOnClickListener {
                activity.let {
                    val login = Intent (context, LoginActivity::class.java)
                    startActivity(login)
                }
            }

            tvSignUp.setOnClickListener {
                activity.let {
                    val signUp = Intent (context, LoginActivity::class.java)
                    startActivity(signUp)
                }
            }
        }
        return binding.root
    }
}