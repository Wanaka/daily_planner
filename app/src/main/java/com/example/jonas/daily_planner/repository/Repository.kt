package com.example.jonas.daily_planner.repository

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.example.jonas.daily_planner.model.Planner
import javax.inject.Inject

class Repository @Inject constructor() {


    fun addPlannerDummy(): LiveData<List<Planner>> {
        var mutableList = MutableLiveData<List<Planner>>()
        val plannerDummy: ArrayList<Planner> = ArrayList()
        plannerDummy.add(Planner("Working", "My awesome project", 9, 8, 3, false))
        mutableList.value = plannerDummy

        return mutableList
    }

    fun calculateWakeUpTime(): LiveData<List<String>>{
        var mutableList = MutableLiveData<List<String>>()

        var wakeUpHour = 7
        var numbers: IntArray = intArrayOf(0, 1, 2, 3, 4, 5, 6 ,7, 8, 9,
            10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23)

        var countBefore = 0
        var countAfter = 0

        var beforeList: ArrayList<String> = ArrayList()
        var afterList: ArrayList<String> = ArrayList()

        for (count in numbers)
            if (count < wakeUpHour) {
                beforeList.add(countBefore, lessThan10(numbers[count]))
                countBefore++
            } else {
                afterList.add(countAfter, lessThan10(numbers[count]))
                countAfter++
            }

        beforeList.sort()
        afterList.sort()

        mutableList.value = afterList + beforeList
        return mutableList
    }



    fun lessThan10(number: Int): String{
        return if (number<10) "0${number}"
        else "$number"
    }
}