package com.example.jonas.daily_planner.ui

import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import com.example.jonas.daily_planner.R
import com.example.jonas.daily_planner.base.BaseActivity
import com.example.jonas.daily_planner.model.Planner
import kotlinx.android.synthetic.main.item_popup.*
import java.io.Serializable

class PlannerActivity : BaseActivity(), PopUpFragment.Communicator {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_planner)

        supportFragmentManager.beginTransaction()
            .add(R.id.fragment_container, PlannerListFragment())
            .commit()
    }

    override fun passDataFromPopFragmentToPlannerFragment(item: Planner) {
        val bundle = Bundle()
        bundle.putSerializable(KEY_POP_FRAGMENT_DATA, item as Serializable)

        val passData = PlannerListFragment()
        passData.arguments = bundle

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, passData)
            .commit()
    }


    companion object {
        const val KEY_POP_FRAGMENT_DATA = "KEY_POP_FRAGMENT_DATA"
    }
}
