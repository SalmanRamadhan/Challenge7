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
        binding?.apply { registerForContextMenu(this.ivOpenPopMenu) }

        binding?.ivOpenPopMenu?.setOnClickListener {
            activity?.openContextMenu(it)
        }
        return binding?.root
    }

    override fun onCreateContextMenu(
        menu: ContextMenu,
        v: View,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        requireActivity().menuInflater.inflate(R.menu.menu_pop_up, menu)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {

        when (item.itemId){
            R.id.menu_setting -> {
                toSetting()
            }
            R.id.menu_Logout -> logout()
        }
        return super.onContextItemSelected(item)
    }

    private fun toSetting(){
        val intent = Intent (requireContext(), SettingActivity::class.java)
        startActivity(intent)
    }

    private fun logout(){
        activity?.finish()
    }



}