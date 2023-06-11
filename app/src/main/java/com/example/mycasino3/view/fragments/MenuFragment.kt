package com.example.mycasino3.view.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.mycasino3.R
import com.example.mycasino3.constant.MAIN
import com.example.mycasino3.constant.url_dice
import kotlinx.android.synthetic.main.fragment_menu.*

class MenuFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_menu, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        id_menu_start_game.setOnClickListener {
            MAIN.navController.navigate(R.id.action_menuFragment_to_gameFragment)
        }


        id_menu_settings.setOnClickListener {
            MAIN.navController.navigate(R.id.action_menuFragment_to_settingsFragment)
        }


        id_menu_exit.setOnClickListener {
            MAIN.finishAffinity()
        }

        Glide.with(requireContext())
            .load(url_dice)
            .into(id_menu_img_dice)



    }




}