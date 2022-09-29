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
                    val login = Intent (requireContext(), LoginActivity::class.java)
                    login.putExtra("fragment", 1)
                    startActivity(login)
                }
            }

            tvSignUp.setOnClickListener {
                activity.let {
                    val signUp = Intent (requireContext(), LoginActivity::class.java)
                    signUp.putExtra("fragment", 2)
                    startActivity(signUp)
                }
            }
        }
        return binding.root
    }
}