package com.ironhidegames.android.myapplication.api.players

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)

data class Substitutes(
    val bench: Int?,
    val `in`: Int?,
    val `out`: Int?
)