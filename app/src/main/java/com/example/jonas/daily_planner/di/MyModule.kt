package com.example.jonas.daily_planner.di

import android.arch.lifecycle.ViewModelProvider
import com.example.jonas.daily_planner.navigator.NavigatorImpl
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
abstract class MyModule {

    @Binds
    abstract fun bindViewModelFactory(factory: MyViewModelFactory): ViewModelProvider.Factory
}