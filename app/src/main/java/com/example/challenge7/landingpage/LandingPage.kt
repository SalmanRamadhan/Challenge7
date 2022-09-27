package com.example.challenge7.landingpage

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.challenge7.R
import com.example.challenge7.authentication.LoginFragment
import com.example.challenge7.databinding.ActivityLandingPageBinding

class LandingPage : AppCompatActivity() {
    var binding: ActivityLandingPageBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLandingPageBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        val viewpagerAdapter = LandingPageAdapter(this)

        binding?.apply {
            vpLandingPage.adapter = viewpagerAdapter
            dotIndicator.attachTo(vpLandingPage)
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        val viewPager = binding?.vpLandingPage
        if (viewPager?.currentItem != 0) {
            viewPager?.setCurrentItem(viewPager.currentItem - 1, true)
        } else {
            finish()
        }
    }
}