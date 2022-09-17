package com.example.challenge7.landingpage

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
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

    override fun onBackPressed() {
        val viewPager = binding?.vpLandingPage
        if (viewPager?.currentItem != 0) {
            viewPager?.setCurrentItem(viewPager.currentItem - 1,true)
        }else{
            finish()
        }
    }
}