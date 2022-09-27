package com.example.challenge7.model

data class UserData(
    val _id: String,
    var email: String,
    var username: String,
    val token: String,
    var photo: String = ""
)