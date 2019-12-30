package com.example.jonas.daily_planner.di

import android.content.Context
import com.example.jonas.daily_planner.navigator.NavigatorImpl
import com.example.jonas.daily_planner.repository.FireStoreService
import com.example.jonas.daily_planner.repository.Repository
import com.example.jonas.daily_planner.util.Date
import com.example.jonas.daily_planner.util.Key
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
        return Repository(FireStoreService(Date()))
    }

    @Provides
    open fun fireStoreService(): FireStoreService {
        return FireStoreService(Date())
    }

    @Provides
    open fun date(): Date {
        return Date()
    }

//    @Provides
//    open fun key(): Key {
//        return Key()
//    }
}