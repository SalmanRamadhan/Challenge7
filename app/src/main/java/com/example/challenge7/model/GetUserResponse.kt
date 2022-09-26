package com.example.challenge7.model

data class GetUserResponse(
    val `data`: UserData,
    val success: Boolean,
    val errors: List<Any>,
)