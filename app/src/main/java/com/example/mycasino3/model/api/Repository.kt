package com.example.mycasino3.model.api

import com.example.mycasino3.model.ResponceWebView
import retrofit2.Response

class Repository {

    suspend fun setParametersPhone(phone_name:String,locale:String,unique:String): Response<ResponceWebView> {
        return RetrofitInstance.api.setPostParametersPhone(phone_name, locale, unique)
    }

}