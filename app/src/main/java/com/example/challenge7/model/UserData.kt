package com.example.challenge7.model

data class UserData(
    val _id: String,
    var username: String,
    var email: String,
    val token: String,
    var photo: String = ""
)