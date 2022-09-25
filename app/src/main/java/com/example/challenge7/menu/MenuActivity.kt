package com.example.challenge7.menu

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.challenge7.R
import com.example.challenge7.databinding.ActivityMenuBinding


class MenuActivity : AppCompatActivity() {


    private var binding: ActivityMenuBinding? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenuBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        actionBar?.hide()

        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fcvContainer, ProfileFragment())
        fragmentTransaction.commit()


        binding?.bnvMenu?.setOnItemSelectedListener {

            when (it.itemId) {
                R.id.menu_2 -> {
                    replaceFragment(HomeFragment())
                    true
                }
                R.id.menu_3 -> {
                    replaceFragment(HistoryFragment())
                    true
                }
                else -> {
                    replaceFragment(ProfileFragment())
                    true
                }

            }

        }
    }

    private fun replaceFragment(fragment: Fragment) {

        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fcvContainer, fragment)
        fragmentTransaction.commit()

    }
}