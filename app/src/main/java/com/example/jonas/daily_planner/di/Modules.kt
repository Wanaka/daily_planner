package com.example.jonas.daily_planner.di

import com.example.jonas.daily_planner.navigator.NavigatorImpl
import com.example.jonas.daily_planner.repository.FireStoreService
import com.example.jonas.daily_planner.repository.Repository
import dagger.Module
import dagger.Provides

@Module
class Modules {

    @Provides
    open fun navigator(): NavigatorImpl {
        return NavigatorImpl()
    }

    @Provides
    open fun repository(): Repository {
        return Repository(FireStoreService())
    }

    @Provides
    open fun fireStoreService(): FireStoreService {
        return FireStoreService()
    }
}