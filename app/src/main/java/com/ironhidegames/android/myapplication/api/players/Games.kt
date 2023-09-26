package com.ironhidegames.android.myapplication.api.players

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)

data class Games(
    val appearences: Int?,
    val captain: Boolean?,
    val lineups: Int?,
    val minutes: Int?,
    val number: Any?,
    val position: String?,
    val rating: String?
)