package com.example.jonas.daily_planner.util

import android.content.Context
import android.util.Log
import javax.inject.Inject

class SharedPreferenceDate @Inject constructor() {

    fun saveDate(context: Context, date: String) {
        val sharedPreference = context.getSharedPreferences("DATE_DATA", Context.MODE_PRIVATE)
        var editor = sharedPreference.edit()
        editor.putString("date", date)
        editor.apply()
    }

    fun getDate(context: Context): String {
        val sharedPreference = context!!.getSharedPreferences("DATE_DATA", Context.MODE_PRIVATE)

        return sharedPreference.getString("date", "")
    }
}