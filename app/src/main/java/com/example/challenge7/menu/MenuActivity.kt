package com.example.challenge7.menu

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.challenge7.R
import com.example.challenge7.databinding.ActivityMenuBinding
import com.example.challenge7.history.room.HistoryDatabase


class MenuActivity : AppCompatActivity() {


    private var binding: ActivityMenuBinding? = null

    private val viewModel: MenuViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenuBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        actionBar?.hide()



        binding?.bnvMenu?.setOnItemSelectedListener {

            when (it.itemId) {
                R.id.menu_1 -> {
                    replaceFragment(ProfileFragment())
                    true
                }
                R.id.menu_2 -> {
                    replaceFragment(HomeFragment())
                    true
                }
                R.id.menu_3 -> {
                    replaceFragment(HistoryFragment())
                    true
                }
                else -> false

            }

        }
    }

    private fun replaceFragment(fragment: Fragment) {

        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fcvContainer, fragment)
        fragmentTransaction.commit()

    }
}