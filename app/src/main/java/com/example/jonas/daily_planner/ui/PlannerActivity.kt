package com.example.jonas.daily_planner.ui

import android.os.Bundle
import com.example.jonas.daily_planner.R
import com.example.jonas.daily_planner.base.BaseActivity

class PlannerActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_planner)

        supportFragmentManager.beginTransaction().add(R.id.fragment_container,
            PlannerListFragment()
        ).commit()
    }

}
