package com.ironhidegames.android.myapplication.api.players

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)

data class Penalty(
    val commited: Any?,
    val missed: Int?,
    val saved: Int?,
    val scored: Int?,
    val won: Any?
)