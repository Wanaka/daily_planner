package com.example.jonas.daily_planner.di

import com.example.jonas.daily_planner.ui.PlannerListFragment
import dagger.Component

@Component(modules = [MyModule::class, Modules::class])
interface MyComponent {
    fun inject(activity: PlannerListFragment)
}