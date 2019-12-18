package com.example.jonas.daily_planner.di

import com.example.jonas.daily_planner.ui.PlannerListFragment
import com.example.jonas.daily_planner.ui.PlannerViewModel
import dagger.Component

@Component(modules = [ViewModelFactoryModule::class, Modules::class])
interface AppComponent {

    fun inject(activity: PlannerListFragment)
    fun inject(viewModel: PlannerViewModel)

}