package com.ironhidegames.android.myapplication.converter

import com.ironhidegames.android.myapplication.api.players.Players
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import org.json.JSONObject

fun JSONObject.toPlayers(): Players? {
    val moshi = Moshi.Builder().build()
    val adapter: JsonAdapter<Players> = moshi.adapter(Players::class.java)
    return adapter.fromJson(this.toString())
}