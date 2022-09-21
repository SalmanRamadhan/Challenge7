package com.example.challenge7.api

import com.example.challenge7.model.GetUserResponse
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ApiService {

    @FormUrlEncoded
    @POST("auth/register")
    fun register(
        @Field("email") email: String,
        @Field("username") username: String,
        @Field("password") password: String
    ): Call<GetUserResponse>

    @FormUrlEncoded
    @POST("auth/login")
    fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<GetUserResponse>
}