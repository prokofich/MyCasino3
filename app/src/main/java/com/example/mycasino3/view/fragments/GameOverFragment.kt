package com.example.mycasino3.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.mycasino3.R
import com.example.mycasino3.constant.KEY_POINT
import com.example.mycasino3.constant.MAIN
import com.example.mycasino3.constant.url_dice
import kotlinx.android.synthetic.main.fragment_game_over.*

class GameOverFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_game_over, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var myPoints = requireArguments().getInt(KEY_POINT)

        loadImageDice()
        id_gameover_pointscored.text = "points scored: ${myPoints}"
        id_gameover_last_record.text = "previous record: ${MAIN.getRecord()}"

        if(myPoints > MAIN.getRecord()){
            id_gameover_message.text = "Great!You have broken your record!Congratulate"
            MAIN.setRecord(myPoints)
        }else{
            id_gameover_message.text = "Unfortunately, this time you failed to beat the previous record"
        }

        id_gameover_button_again.setOnClickListener {
            MAIN.navController.navigate(R.id.action_gameOverFragment_to_gameFragment)
        }

        id_gameover_button_to_menu.setOnClickListener {
            MAIN.navController.navigate(R.id.action_gameOverFragment_to_menuFragment)
        }

    }

    private fun loadImageDice(){
        Glide.with(requireContext())
            .load(url_dice)
            .into(id_gameover_img_dice)
    }

}