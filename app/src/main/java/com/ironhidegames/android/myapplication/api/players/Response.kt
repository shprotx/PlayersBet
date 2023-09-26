package com.ironhidegames.android.myapplication.api.players

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)

data class Response(
    val player: Player,
    val statistics: List<Statistic>
)