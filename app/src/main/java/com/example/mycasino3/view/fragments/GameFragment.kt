package com.example.mycasino3.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.example.mycasino3.R
import com.example.mycasino3.constant.*
import kotlinx.android.synthetic.main.fragment_game.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class GameFragment : Fragment() {

    var oppenent_point1 = 0
    var oppenent_point2 = 0
    var my_point1 = 0
    var my_point2 = 0
    var correct = 0

    var flagShowOpponentDice = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_game, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        showToast("use your intuition to choose the appropriate answer.click on the start button")
        loadImageDice()
        loadDiceQuestion()


        /////////////////////НАЧАТЬ ИГРУ/////////////////////////
        id_game_go.setOnClickListener {
            showToast("toss the opponent's dice or choose the parity of points")
            it.isVisible = false

            id_game_view_linea1.isVisible = true
            id_game_view_linea2.isVisible = true
            id_game_tv_correct.isVisible = true

            id_game_even.isEnabled = true
            id_game_odd.isEnabled = true
            id_game_button_toss_dice.isEnabled = true
        }
        ////////////////////////////////////////////////////////


        ///////////////////КИНУТЬ КОСТИ СОПЕРНИКА////////////////
        id_game_button_toss_dice.setOnClickListener {
            id_game_title_opponent.isVisible = true
            id_game_opp_ll.isVisible = true

            id_game_more.isEnabled = true
            id_game_less.isEnabled = true

            id_game_even.isEnabled = false
            id_game_odd.isEnabled = false

            flagShowOpponentDice = true


            id_game_img_dice.isVisible = false

            showDiceOpponent()

        }
        ////////////////////////////////////////////////////////


        //ЧЕТНЫЕ ОЧКИ///////////////////////////////////////////
        id_game_even.setOnClickListener {
            id_game_opp_ll.isVisible = false
            id_game_title_opponent.isVisible = false
            showDiceMy()
            if((my_point1+my_point2)%2==0){
                rightAnswer()
            }else{
                wrongAnswer()
            }
        }
        ////////////////////////////////////////////////////////

        //НЕЧЕТНЫЕ ОЧКИ/////////////////////////////////////////
        id_game_odd.setOnClickListener {
            id_game_opp_ll.isVisible = false
            id_game_title_opponent.isVisible = false
            showDiceMy()
            if((my_point1+my_point2)%2!=0){
                rightAnswer()
            }else{
                wrongAnswer()
            }
        }
        ///////////////////////////////////////////////////////

        //МОИХ ОЧКОВ БОЛЬШЕ////////////////////////////////////
        id_game_more.setOnClickListener {

            id_game_even.isEnabled = true
            id_game_odd.isEnabled = true

            if(flagShowOpponentDice){

                showDiceMy()
                if(my_point1+my_point2>oppenent_point1+oppenent_point2){
                    loadDiceQuestionOpponent()
                    rightAnswer()
                }else{
                    wrongAnswer()
                }

            }else{
                showToast("first, toss the opponent's dice")
            }
        }
        ///////////////////////////////////////////////////////

        //МОИХ ОЧКОВ МЕНЬШЕ///////////////////////////////////
        id_game_less.setOnClickListener {

            id_game_even.isEnabled = true
            id_game_odd.isEnabled = true

            if(flagShowOpponentDice){

                showDiceMy()
                if(my_point1+my_point2<oppenent_point1+oppenent_point2){
                    loadDiceQuestionOpponent()
                    rightAnswer()
                }else{
                    wrongAnswer()
                }

            }else{
                showToast("first, toss the opponent's dice")
            }
        }
        /////////////////////////////////////////////////////

    }

    private fun loadDiceQuestion(){
        Glide.with(requireContext())
            .load(url_dice_question)
            .into(id_game_dice_my1)
        Glide.with(requireContext())
            .load(url_dice_question)
            .into(id_game_dice_my2)
    }

    private fun loadDiceQuestionOpponent(){
        CoroutineScope(Dispatchers.Main).launch {
            delay(3000)
            Glide.with(requireContext())
                .load(url_dice_question)
                .into(id_game_dice_op1)
            Glide.with(requireContext())
                .load(url_dice_question)
                .into(id_game_dice_op2)
        }
    }

    private fun loadImageDice(){
        Glide.with(requireContext())
            .load(url_dice)
            .into(id_game_img_dice)
    }

    private fun showDiceOpponent(){
        oppenent_point1 = listPoints.shuffled()[1]
        oppenent_point2 = listPoints.shuffled()[1]

        Glide.with(requireContext())
            .load(url_dice_point+"${oppenent_point1}.png")
            .into(id_game_dice_op1)
        Glide.with(requireContext())
            .load(url_dice_point+"${oppenent_point2}.png")
            .into(id_game_dice_op2)
    }

    private fun showDiceMy(){
        my_point1 = listPoints.shuffled()[1]
        my_point2 = listPoints.shuffled()[1]

        Glide.with(requireContext())
            .load(url_dice_point+"${my_point1}.png")
            .into(id_game_dice_my1)
        Glide.with(requireContext())
            .load(url_dice_point+"${my_point2}.png")
            .into(id_game_dice_my2)
    }

    private fun showToast(message:String){
        Toast.makeText(requireContext(),message,Toast.LENGTH_SHORT).show()
    }

    private fun allButtonToEnableFalse(){
        id_game_even.isEnabled = false
        id_game_odd.isEnabled = false
        id_game_more.isEnabled  = false
        id_game_less.isEnabled = false
        id_game_button_toss_dice.isEnabled = false
    }

    private fun rightAnswer(){
        showToast("well done!right!")
        correct+=1
        id_game_tv_correct.text = "correct: ${correct}"
        CoroutineScope(Dispatchers.Main).launch{
            delay(3000)
            loadDiceQuestion()
        }
    }

    private fun wrongAnswer(){
        var bundle = Bundle()
        bundle.putInt(KEY_POINT,correct)
        allButtonToEnableFalse()
        showToast("unfortunately you didn't guess")
        CoroutineScope(Dispatchers.Main).launch{
            delay(3000)
            MAIN.navController.navigate(R.id.action_gameFragment_to_gameOverFragment,bundle)
        }
    }





}