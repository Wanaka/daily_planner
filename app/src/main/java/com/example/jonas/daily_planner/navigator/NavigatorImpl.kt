package com.example.jonas.daily_planner.navigator

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.util.Log
import com.example.jonas.daily_planner.model.Planner
import com.example.jonas.daily_planner.ui.PlannerListFragment
import javax.inject.Inject

class NavigatorImpl @Inject constructor() : Navigator {
    override fun newEvent(context: Context, item: Planner) {
        Log.d(",,,", "HEJSAn newEvent func ${item.startTime}")


    }
}