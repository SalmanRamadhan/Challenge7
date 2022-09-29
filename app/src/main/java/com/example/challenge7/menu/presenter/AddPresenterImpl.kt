package com.example.challenge7.menu.presenter

import com.example.challenge7.api.NetworkHelper
import com.example.challenge7.menu.AddView
import com.example.challenge7.menu.MenuActivity
import com.example.challenge7.model.GetUserProfileResponse
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class AddPresenterImpl(val view: AddView) : AddPresenterContract {

    override fun fetchInputPosting(token: String) {
        NetworkHelper.instance.getUser("Bearer $token").enqueue(object :
        Callback<GetUserProfileResponse>{
            override fun onResponse(
                call: Call<GetUserProfileResponse>,
                response: Response<GetUserProfileResponse>
            ) {
                val data = response.body()?.data.toString()
                val dataJson = Gson().toJson(response.body(),GetUserProfileResponse::class.java)

                if(response.code() == 200){
                    view.addResultSuccess(dataJson)
                    view.hideLoading()
                } else {
                    view.addResultFailed("Data gagal tersimpan")
                }
            }

            override fun onFailure(call: Call<GetUserProfileResponse>, t: Throwable) {
                if(t is IOException){
                    view.addResultFailed("Koneksi bermasalah")
                } else {
                    view.addResultFailed(t.message?:"")
                }
            }
        })
    }
}