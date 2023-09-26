package com.ironhidegames.android.myapplication.api.players

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)

data class Goals(
    val assists: Int?,
    val conceded: Int?,
    val saves: Int?,
    val total: Int?
)