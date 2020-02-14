package com.example.jonas.daily_planner.navigator

import android.content.Context
import androidx.fragment.app.FragmentManager
import com.example.jonas.daily_planner.model.Planner

interface Navigator {
    fun newEvent(context: Context, fm: FragmentManager, item: Planner)
    fun openHourPopup(fm: FragmentManager)
}