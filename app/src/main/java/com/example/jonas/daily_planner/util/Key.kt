package com.example.jonas.daily_planner.util

import android.content.Context
import android.preference.PreferenceManager
import android.util.Log
import java.util.*


class Key constructor(val context: Context){
    private val pref = PreferenceManager.getDefaultSharedPreferences(context)

    fun UUID(){
        if(!pref.contains("KEY")){

            val editor = pref.edit()

            editor
                .putString("KEY", UUID.randomUUID().toString())
                .apply()
        }
    }

    fun getUUID(): String {
        val pref = PreferenceManager.getDefaultSharedPreferences(context)
        return pref.getString("KEY", "")!!
    }
}