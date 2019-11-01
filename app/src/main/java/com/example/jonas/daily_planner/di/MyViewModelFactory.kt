package com.example.jonas.daily_planner.di

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.example.jonas.daily_planner.ui.PlannerViewModel
import javax.inject.Inject
import javax.inject.Provider

class MyViewModelFactory @Inject constructor(private val myviewmodelprovider: Provider<PlannerViewModel>): ViewModelProvider.Factory {

    @Suppress(names = ["UNCHECKED_CAST"])
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return myviewmodelprovider.get() as T
    }
}