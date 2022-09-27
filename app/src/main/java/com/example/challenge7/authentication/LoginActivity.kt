package com.example.challenge7.authentication

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.get
import androidx.fragment.app.Fragment
import com.example.challenge7.R
import com.example.challenge7.databinding.ActivityLoginBinding
import com.example.challenge7.helper.SharedPreferences
import com.example.challenge7.menu.HomeFragment
import com.example.challenge7.menu.MenuActivity

class LoginActivity : AppCompatActivity() {

    val fragmentLogin: Fragment = LoginFragment()
    val fragmentSignUp: Fragment = SignUpFragment()
    val fragmentHome: Fragment = HomeFragment()

    private val SharedPreferences by lazy { SharedPreferences(this) }

    var binding: ActivityLoginBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        intent.getIntExtra("fragment", 1).let {
            when (it){
                1 -> callFragment(fragmentLogin)
                2 -> callFragment(fragmentSignUp)
            }
        }



        //cek apakah user udah login atau belum
        if (SharedPreferences.getStatusLogin()) {
            //jika sudah login langsung pindah ke home
//            callFragment(fragmentHome)
            val intent = Intent(this, MenuActivity::class.java)
            startActivity(intent)
           finish()
        }/* else {
            //jika belum login tampilkan login
            callFragment(fragmentLogin)
        }
*/
    }

    private fun callFragment(fragment: Fragment){
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.add(R.id.fcvAuth,fragment)
        fragmentTransaction.commit()

    }
}