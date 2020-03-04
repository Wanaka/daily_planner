package com.example.jonas.daily_planner.navigator

import android.content.Context
import androidx.fragment.app.FragmentManager
import com.example.jonas.daily_planner.model.Planner
import com.example.jonas.daily_planner.model.WakeHoursModel
import com.example.jonas.daily_planner.view.PopUpFragment
import com.example.jonas.daily_planner.view.TimePickerFragment
import javax.inject.Inject

class NavigatorImpl @Inject constructor() : Navigator {

    override fun newEvent(context: Context, fm: FragmentManager, item: Planner) {
        var popupDialog = PopUpFragment.newInstance(item)
        popupDialog.show(fm, "popup_fragment")
    }

    override fun openHourPopup(wakeHours: WakeHoursModel, fm: FragmentManager) {
        var popupDialog = TimePickerFragment.newInstance(wakeHours)
        popupDialog.show(fm, "time_fragment")
    }
}