package com.ironhidegames.android.myapplication.api.players

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)

data class Tackles(
    val blocks: Int?,
    val interceptions: Int?,
    val total: Int?
)