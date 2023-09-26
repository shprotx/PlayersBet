package com.ironhidegames.android.myapplication.api.players

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)

data class Fouls(
    val committed: Int?,
    val drawn: Int?
)