package com.example.challenge7.landingpage

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class LandingPageAdapter(fragmentActivity : FragmentActivity): FragmentStateAdapter(fragmentActivity) {
    private val pages = 3
    override fun getItemCount(): Int {
        return pages
    }
    override fun createFragment(position: Int) : Fragment {

    return  when (position){
        0->{
            LandingFragment1()
        }
        1-> LandingFragment2()

        else -> {
            LandingFragment3()
        }
    }
    }
}