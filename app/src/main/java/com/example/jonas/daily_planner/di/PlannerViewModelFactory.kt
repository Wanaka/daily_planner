package com.example.jonas.daily_planner.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.jonas.daily_planner.ui.PlannerViewModel
import javax.inject.Inject
import javax.inject.Provider

class PlannerViewModelFactory @Inject constructor(private val plannerViewModelProvider: Provider<PlannerViewModel>): ViewModelProvider.Factory {

    @Suppress(names = ["UNCHECKED_CAST"])
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return plannerViewModelProvider.get() as T
    }
}