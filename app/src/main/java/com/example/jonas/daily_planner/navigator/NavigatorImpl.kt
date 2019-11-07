package com.example.jonas.daily_planner.navigator

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.support.v4.app.FragmentManager
import android.util.Log
import com.example.jonas.daily_planner.model.Planner
import com.example.jonas.daily_planner.ui.PlannerListFragment
import com.example.jonas.daily_planner.ui.PopUpFragment
import javax.inject.Inject

class NavigatorImpl @Inject constructor() : Navigator {
    override fun newEvent(context: Context, fm: FragmentManager, item: Planner) {
        var alertDialog = PopUpFragment.newInstance(item)
        alertDialog.show(fm, "popup_fragment")
    }
}