package com.ironhidegames.android.myapplication.api.players

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)

data class Birth(
    val country: String?,
    val date: String?,
    val place: String?
)