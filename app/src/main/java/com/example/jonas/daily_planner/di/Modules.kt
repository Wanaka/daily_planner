package com.example.jonas.daily_planner.di

import com.example.jonas.daily_planner.navigator.NavigatorImpl
import dagger.Module
import dagger.Provides

@Module
class Modules {
    @Provides
    open fun navigator(): NavigatorImpl {
        return NavigatorImpl()
    }
}