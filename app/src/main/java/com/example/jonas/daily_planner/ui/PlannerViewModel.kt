package com.example.jonas.daily_planner.ui

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import com.example.jonas.daily_planner.model.Planner
import com.example.jonas.daily_planner.repository.Repository
import javax.inject.Inject

class PlannerViewModel @Inject constructor(private val repository: Repository): ViewModel() {

    fun callDummyData(): LiveData<List<Planner>> {
        return repository.addPlannerDummy()
    }

    fun callWakeUpTime(): LiveData<List<Int>>{
        return repository.calculateWakeUpTime()
    }
}