package com.example.jonas.daily_planner.di

import com.example.jonas.daily_planner.repository.FireStoreService
import com.example.jonas.daily_planner.repository.Repository
import com.example.jonas.daily_planner.view.PlannerActivity
import com.example.jonas.daily_planner.view.PlannerListFragment
import com.example.jonas.daily_planner.view.PlannerViewModel
import dagger.Component

@Component(modules = [ViewModelFactoryModule::class, Modules::class])
interface AppComponent {

    fun inject(activity: PlannerActivity)
    fun inject(fragment: PlannerListFragment)
    fun inject(repo: Repository)
    fun inject(viewModel: PlannerViewModel)
    fun inject(fireStore: FireStoreService)
}