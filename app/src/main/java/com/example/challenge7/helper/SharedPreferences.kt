package com.example.challenge7.helper

import android.content.Context
import android.content.SharedPreferences
import androidx.fragment.app.FragmentActivity
import com.example.challenge7.model.UserData
import com.google.gson.Gson

class SharedPreferences(activity: FragmentActivity){

    val login = "Login"
    val myPref = "Main_Pref"
    val sharedPreference: SharedPreferences

    val user = "User"

    init {
        sharedPreference = activity.getSharedPreferences(myPref, Context.MODE_PRIVATE)
    }

    fun setStatusLogin(status: Boolean){
        sharedPreference.edit().putBoolean(login, status).apply()
    }

    fun getStatusLogin():Boolean{
        return sharedPreference.getBoolean(login, false)
    }

    fun setUser(value : UserData){
        //ubah dari data object ke dta string
        val data = Gson().toJson(value, UserData::class.java)
        sharedPreference.edit().putString(user, data).apply()
    }

    fun getUser(): UserData?{
        //ubah dari data string ke data object
        val data = sharedPreference.getString(user, null) ?: return null
        return Gson().fromJson<UserData>(data, UserData::class.java)
    }

    //delete data user
    fun deleteUser(){
        sharedPreference.edit().remove(user).apply()
    }

}