package com.example.challenge7.menu

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.PopupMenu
import android.widget.RelativeLayout
import com.example.challenge7.R
import com.example.challenge7.databinding.FragmentHomeBinding
import com.example.challenge7.setting.SettingActivity


class HomeFragment : Fragment() {
    private var binding : FragmentHomeBinding? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?
    {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
//        binding?.apply { registerForContextMenu(this.ivOpenPopMenu) }

        binding?.ivOpenPopMenu?.setOnClickListener {
            val popup = PopupMenu(activity,binding?.ivOpenPopMenu)
            popup.inflate(R.menu.menu_pop_up)
            popup.setOnMenuItemClickListener {
                when (it.itemId){
                    R.id.menu_setting-> {
                        toSetting()
                        true
                    }
                    R.id.menu_Logout -> {
                        logout()
                        true
                    }
                    else -> false
                }
            }
            popup.show()
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