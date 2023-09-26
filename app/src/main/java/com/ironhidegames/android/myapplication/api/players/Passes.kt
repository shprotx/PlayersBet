package com.ironhidegames.android.myapplication.api.players

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)

data class Passes(
    val accuracy: Int?,
    val key: Int?,
    val total: Int?
)