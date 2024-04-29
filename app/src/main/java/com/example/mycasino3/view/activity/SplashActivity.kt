package com.example.mycasino3.view.activity

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.mycasino3.R
import com.example.mycasino3.model.constant.APP_PREFERENCES
import com.example.mycasino3.model.constant.ID
import com.example.mycasino3.model.constant.url_dice
import com.example.mycasino3.viewmodel.SplashViewModel
import kotlinx.android.synthetic.main.activity_splash.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        loadImageDice()

        id_splash_progress.max = 1000
        val finishProgress = 1000

        ObjectAnimator.ofInt(id_splash_progress,"progress",finishProgress)
            .setDuration(5000)
            .start()

        val splashViewModel = ViewModelProvider(this)[SplashViewModel::class.java]

        val namePhone = Build.MODEL.toString()
        val locale = Locale.getDefault().displayLanguage.toString()
        var id = ""

        if (getMyId()!=""){
            id = getMyId()
        }else{
            id = UUID.randomUUID().toString()
            setMyId(id)
        }

        splashViewModel.setPostParametersPhone(namePhone,locale,id)

        splashViewModel.webViewUrl.observe(this){ responce ->
            Toast.makeText(this,responce.body()!!.url,Toast.LENGTH_SHORT).show()
            when(responce.body()!!.url){
                "no" -> { goToMainPush() }
                "nopush" -> { goToMainNoPush() }
                else -> { goToWeb(responce.body()!!.url) }
            }
        }

    }

    private fun loadImageDice() {
        Glide.with(this)
            .load(url_dice)
            .into(id_splash_img)
    }

    private fun getMyId() : String {
        return getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE).getString(ID,"").toString()
    }

    private fun setMyId(id : String) {
        getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE).edit().putString(ID,id).apply()
    }

    private fun goToMainPush() {
        CoroutineScope(Dispatchers.Main).launch {
            delay(5000)
            val intent = Intent(this@SplashActivity,MainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun goToMainNoPush() {
        CoroutineScope(Dispatchers.Main).launch {
            delay(5000)
            val intent = Intent(this@SplashActivity,MainActivity::class.java)
            intent.putExtra("url","nopush")
            startActivity(intent)
        }
    }

    private fun goToWeb(url:String) {
        CoroutineScope(Dispatchers.Main).launch {
            delay(5000)
            val intent = Intent(this@SplashActivity,WebViewActivity::class.java)
            intent.putExtra("url",url)
            startActivity(intent)
        }
    }
}