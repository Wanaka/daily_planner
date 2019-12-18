package com.example.jonas.daily_planner.ui

import android.os.Bundle
import com.example.jonas.daily_planner.R
import com.example.jonas.daily_planner.base.BaseActivity

class PlannerActivity : BaseActivity(), PopUpFragment.Communicator {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_planner)

        supportFragmentManager.beginTransaction()
            .add(R.id.fragment_container, PlannerListFragment())
            .commit()
    }

    override fun passDataFromPopFragmentToPlannerFragment(editext_input: String) {
        val bundle = Bundle()
        bundle.putString(KEY_POP_FRAGMENT_DATA,editext_input)

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
