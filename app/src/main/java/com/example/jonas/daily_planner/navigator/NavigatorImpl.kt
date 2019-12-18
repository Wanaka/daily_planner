package com.example.jonas.daily_planner.navigator

import android.content.Context
import androidx.fragment.app.FragmentManager
import com.example.jonas.daily_planner.model.Planner
import com.example.jonas.daily_planner.ui.PopUpFragment
import javax.inject.Inject

class NavigatorImpl @Inject constructor() : Navigator {

    override fun newEvent(context: Context, fm: FragmentManager, item: Planner) {
        var popupDialog = PopUpFragment.newInstance(item)
        popupDialog.show(fm, "popup_fragment")
    }

}