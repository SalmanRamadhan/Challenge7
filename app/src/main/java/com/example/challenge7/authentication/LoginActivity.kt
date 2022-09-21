package com.example.challenge7.authentication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.get
import androidx.fragment.app.Fragment
import com.example.challenge7.R
import com.example.challenge7.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    val fragmentLogin: Fragment = LoginFragment()
    val fragmentSignUp: Fragment = SignUpFragment()
    var active: Fragment = fragmentLogin

    var binding: ActivityLoginBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        callFragment(active)
    }

    private fun callFragment(fragment: Fragment){

        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.add(R.id.fcvAuth,fragment)
        fragmentTransaction.commit()

    }
}