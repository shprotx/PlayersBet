package com.ironhidegames.android.myapplication.api

import android.content.Context
import android.util.Log
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.ironhidegames.android.myapplication.api.players.Players
import com.ironhidegames.android.myapplication.converter.toPlayers


class PlayerApiImplementation(
    private val context: Context
) {

    private val TAG = "FootballApiImplementation"
    private val BASE_URL = "https://v3.football.api-sports.io"

    private val listLeagueIds = listOf(
        "414",
        "416",
        "418",
        "419",
        "423"
    )

    suspend fun getListPlayers(success: (listPlayer: List<Players?>) -> Unit) {
        val listPlayer = mutableListOf<Players?>()
        listLeagueIds.forEach {
            getFootballByDate(it) { player ->
                listPlayer.add(player)
                if (listPlayer.size == listLeagueIds.size)
                    success.invoke(listPlayer.toList())
            }
        }
    }

    suspend fun getFootballByDate(league: String, success: (player: Players?) -> Unit) {
        val queue = Volley.newRequestQueue(context)
        val url = "$BASE_URL/players?league=$league&season=2023"


        val stringReq =
            object : JsonObjectRequest(
                Method.POST, url, null,
                Response.Listener { json ->
                    Log.i("mylog", "results ${json.toString()}") // Здесь преобразуем JSON в строку

                    val results = json.get("results").toString().toInt()

                    if (json != null && results > 0)
                        success.invoke(json.toPlayers())
                    else
                        success.invoke(null)

                },
                Response.ErrorListener { error ->
                    Log.i("mylog", "error = " + error)
                    success.invoke(null)

                }
            ) {

                override fun getHeaders(): MutableMap<String, String> {
                    val headers = HashMap<String, String>()
                    headers["x-rapidapi-key"] = "6b5e993ad23a4d155b992de4e5db82a6"
                    return headers
                }

            }
        queue.add(stringReq)
    }

}