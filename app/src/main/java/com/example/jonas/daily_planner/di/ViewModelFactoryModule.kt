package com.example.jonas.daily_planner.di

import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module

@Module
abstract class ViewModelFactoryModule {

    @Binds
    abstract fun bindViewModelFactory(factory: PlannerViewModelFactory): ViewModelProvider.Factory
}