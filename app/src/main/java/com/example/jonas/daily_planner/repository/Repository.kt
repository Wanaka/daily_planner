package com.example.jonas.daily_planner.repository

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.jonas.daily_planner.model.Planner
import com.example.jonas.daily_planner.model.WakeHoursModel
import javax.inject.Inject
import kotlin.collections.ArrayList

class Repository @Inject constructor(var fireStoreService: FireStoreService) {

    suspend fun postItemToFiB(item: Planner, context: Context, activeDate: String) {
        return fireStoreService.postToFireStore(item, context, activeDate)
    }

    suspend fun postwakeHoursToFiB(wakeHours: WakeHoursModel, context: Context, activeDate: String) {
        return fireStoreService.postWakeHours(wakeHours, context, activeDate)
    }

    suspend fun getwakeHoursToFiB(context: Context, activeDate: String): WakeHoursModel {
        return fireStoreService.getWakeHours(context, activeDate)
    }

    suspend fun deleteItems(number: String, context: Context, activeDate: String) {
        return fireStoreService.deleteItems(number, context, activeDate)
    }

//    fun get(): List<Planner>{
//        return fireStoreService.get()
//    }

    suspend fun getDataFromFireStore(context: Context, getDate:String, wakeHours: WakeHoursModel): ArrayList<Planner> {
        return fireStoreService.getDataFromFireStore(context, getDate, wakeHours)
    }

    private fun getDefaultPlanner(startTime: Int, duration: Int, type: Int, distance: Int): Planner{
       return Planner("", "", startTime, duration, type, distance,false)
    }

    fun calculateWakeUpTime(): LiveData<List<Int>>{
        var mutableList = MutableLiveData<List<Int>>()
        mutableList.value = calc()

        return mutableList
    }

    private fun calc(): List<Int>{
        var wakeupTime = 7
        var sleepTime = 22
        var index = 0
        var list: ArrayList<Int> = ArrayList()

        while (wakeupTime <= sleepTime){
            list.add(index, wakeupTime)
            wakeupTime++
            index++
        }

        return list
    }

    //sätt in i helper file
    fun lessThan10(number: Int): String{
        return if (number < 10) "0${number}"
        else "$number"
    }
}
