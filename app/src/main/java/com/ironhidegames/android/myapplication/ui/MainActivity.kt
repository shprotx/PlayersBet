package com.ironhidegames.android.myapplication.ui

import android.content.Intent
import android.content.pm.ActivityInfo
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.navigation.findNavController
import com.ironhidegames.android.myapplication.R
import com.ironhidegames.android.myapplication.api.PlayerApiImplementation
import com.ironhidegames.android.myapplication.common.MySharedPreferences
import com.ironhidegames.android.myapplication.data.DataHolder
import com.ironhidegames.android.myapplication.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject lateinit var sharedPreferences: MySharedPreferences
    private lateinit var binding: ActivityMainBinding
    private val holder = DataHolder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getDataFromPrefs()
        orientationRequest()
        navigationSettings()

    }








    private fun getDataFromPrefs() {
        val list = sharedPreferences.favoriteIds
        if (list.isNotEmpty()) {
            holder.favoriteIds.clear()
            holder.favoriteIds.addAll(stringToIntList(list))
        }
    }




    private fun stringToIntList(string: String): MutableList<Int> {
        return string.split(",").map { it.toInt() }.toMutableList()
    }



    private fun navigationSettings() {
        binding.bottomAppNavigation.setOnItemSelectedListener { item ->
            findNavController(R.id.fragment).navigate(item.itemId)
            return@setOnItemSelectedListener true
        }
    }



    private fun orientationRequest() {
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LOCKED
    }

}