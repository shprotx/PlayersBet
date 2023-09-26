package com.ironhidegames.android.myapplication.common

import android.content.Context

class MySharedPreferences(context: Context) {

    private val sharedPreferences = context.getSharedPreferences("footbalPreferences", Context.MODE_PRIVATE)

    var isFirstAppLaunch
        get() = sharedPreferences.getBoolean("firstLaunch", true)
        set(value) = sharedPreferences.edit().putBoolean("firstLaunch", value).apply()

    var favoriteIds
        get() = sharedPreferences.getString("favorite", "") ?: ""
        set(value) = sharedPreferences.edit().putString("favorite", value).apply()

}