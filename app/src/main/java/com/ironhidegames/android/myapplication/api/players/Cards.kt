package com.ironhidegames.android.myapplication.api.players

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Cards(
    val red: Int?,
    val yellow: Int?,
    val yellowred: Int?
)