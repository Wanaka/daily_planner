package com.example.jonas.daily_planner.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.jonas.daily_planner.model.Planner
import com.example.jonas.daily_planner.repository.Repository
import javax.inject.Inject

class PlannerViewModel @Inject constructor(var component: Repository): ViewModel() {

    fun callDummyData(): LiveData<List<Planner>> {
        return component.addPlannerDummy()
    }

    fun callWakeUpTime(): LiveData<List<Int>>{
        return component.calculateWakeUpTime()
    }
}