package com.example.jonas.daily_planner.navigator

import android.content.Context
import com.example.jonas.daily_planner.model.Planner

interface Navigator {
    fun newEvent(context: Context, item: Planner)
}