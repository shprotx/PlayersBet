package com.ironhidegames.android.myapplication.data

import com.ironhidegames.android.myapplication.api.PlayerApiImplementation
import com.ironhidegames.android.myapplication.api.players.Players
import com.ironhidegames.android.myapplication.api.players.Response
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

object DataHolder {

    val players = mutableListOf<Players?>()
    var isAllDataCollected = false
    var currentPlayer: Response? = null

    val favoriteIds = mutableListOf<Int>()

    fun getPlayers(playerApiImplementation: PlayerApiImplementation, finished: ()->Unit) {
        CoroutineScope(Dispatchers.IO).launch{
            playerApiImplementation.getListPlayers{ list ->
                players.addAll(list)
                finished()
            }
        }
    }

}