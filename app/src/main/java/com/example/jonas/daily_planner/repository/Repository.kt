package com.example.jonas.daily_planner.repository

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.util.Log
import com.example.jonas.daily_planner.model.Planner
import javax.inject.Inject

class Repository @Inject constructor() {
    var wakeupTime = 7
    var sleepTime = 22


    fun addPlannerDummy(): LiveData<List<Planner>> {
        var mutableList = MutableLiveData<List<Planner>>()
        val plannerDummy: ArrayList<Planner> = ArrayList()
        plannerDummy.add(Planner("Working", "My awesome project", 8, 3, 1, false))
        plannerDummy.add(Planner("Working", "My awesome project", 19, 1, 1, false))

        val itemList = arrayListOf<Planner>()

        //sätt in i funktion
        var e = 0
        while (e <= sleepTime - wakeupTime){

            if(e == 0){
                itemList.add(e, getDefaultPlanner(e, 1, 0))
            } else {
                itemList.add(e, getDefaultPlanner(e + wakeupTime - 1, 1, -1))
            }

            e++
        }

        var m = 0
        while (m <= sleepTime - wakeupTime){
            var t = 0
            while (t < plannerDummy.size) {
                if (calc()[m] == plannerDummy[t].startTime) {
                    itemList[m + 1] = plannerDummy[t]

                    //sätt in i util helper file
                    var w = 1
                    while (w < plannerDummy[t].duration){
                        itemList[m + 1 + w] = getDefaultPlanner(w, 0, -1)
                        w++
                    }
                }
                t++
            }
            m++
        }

        mutableList.value = itemList

        return mutableList
    }


    fun getDefaultPlanner(startTime: Int, duration: Int, type: Int): Planner{
       return Planner("", "", startTime, duration, type, false)
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
