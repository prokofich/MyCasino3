package com.example.mycasino3.view.activity

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.mycasino3.R
import com.example.mycasino3.model.constant.APP_PREFERENCES
import com.example.mycasino3.model.constant.MAIN
import com.example.mycasino3.model.constant.RECORD
import kotlinx.android.synthetic.main.fragment_menu.*

class MainActivity : AppCompatActivity() {

    var navController : NavController? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        MAIN = this
        navController = Navigation.findNavController(this,R.id.id_nav_host)

    }

    fun getRecord() : Int {
        return getSharedPreferences(APP_PREFERENCES, MODE_PRIVATE).getInt(RECORD, 0)
    }

    fun setRecord(record : Int) {
        getSharedPreferences(APP_PREFERENCES,Context.MODE_PRIVATE).edit().putInt(RECORD,record).apply()
    }

}