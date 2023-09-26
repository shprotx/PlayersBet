package com.ironhidegames.android.myapplication.api.players

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)

data class Dribbles(
    val attempts: Int?,
    val past: Any?,
    val success: Int?
)