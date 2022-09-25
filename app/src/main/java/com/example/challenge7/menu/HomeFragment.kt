package com.example.challenge7.menu

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.fragment.app.Fragment
import com.example.challenge7.R
import com.example.challenge7.databinding.FragmentHomeBinding
import com.example.challenge7.gameplay.AgainstComActivity
import com.example.challenge7.gameplay.AgainstPlayerActivity
import com.example.challenge7.helper.SharedPreferences
import com.example.challenge7.setting.SettingActivity


class HomeFragment : Fragment() {
    private var binding : FragmentHomeBinding? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?
    {

        binding = FragmentHomeBinding.inflate(inflater, container, false)
        binding?.apply {
            ivOpenPopMenu.setOnClickListener {
                val popup = PopupMenu(activity,binding?.ivOpenPopMenu)
                popup.inflate(R.menu.menu_pop_up)
                popup.setOnMenuItemClickListener {
                    when (it.itemId){
                        R.id.menu_setting-> {
                            toSetting()
                            true
                        }
                        R.id.menu_logout -> {
                            logout()
                            true
                        }
                        else -> false
                    }
                }
                popup.show()
            }
        }
        val sharedPreferences = SharedPreferences(requireActivity())
        val name = sharedPreferences.getUser()?.username?:"Username"
        binding?.tvUserName?.text = "Hi $name"
        binding?.tvUserNameVsCpu?.text = "$name VS CPU"
        binding?.tvUserNameVsPlayer?.text = "$name VS Player"

        binding?.apply {
            ivPlayerVsCpu.setOnClickListener {
                val vsCpu = Intent(requireContext(), AgainstComActivity::class.java)
//                vsCpu.putExtra("USER_NAME", )
                startActivity(vsCpu)
            }
            ivPlayerVsPlayer.setOnClickListener {
                val vsPlayer = Intent(requireContext(), AgainstPlayerActivity::class.java)
//                vsPlayer.putExtra("USER_NAME",)
                startActivity(vsPlayer)
            }
        }

        return binding?.root
    }
    private fun toSetting(){
        val intent = Intent (requireContext(), SettingActivity::class.java)
        startActivity(intent)
    }

    private fun logout(){
        activity?.finish()
    }



}