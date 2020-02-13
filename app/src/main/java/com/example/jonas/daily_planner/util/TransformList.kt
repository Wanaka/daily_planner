package com.example.jonas.daily_planner.util

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.jonas.daily_planner.model.Planner

class TransformList {
    var wakeupTime = 7
    var sleepTime = 22

    fun addPlannerDummy(list: ArrayList<Planner>): ArrayList<Planner> {

        var itemList = arrayListOf<Planner>()

        //sätt in i funktion
        var e = 0
        while (e <= sleepTime - wakeupTime) {

            if (e == 0) {
                itemList.add(e, getDefaultPlanner(e, 1, 0, 0))
            } else {
                if (list.isNullOrEmpty()) {

                    itemList.add(
                        e,
                        getDefaultPlanner(
                            e + wakeupTime - 1,
                            1,
                            -1,
                            (sleepTime - wakeupTime) - e + 1
                        )
                    )
                } else {
                    itemList.add(e, getDefaultPlanner(e + wakeupTime - 1, 1, -1, 0))
                }
            }

            e++
        }

        var m = 0
        while (m <= sleepTime - wakeupTime) {
            var t = 0
            while (t < list.size) {
                if (calc()[m] == list[t].startTime) {
                    //add item to RV
                    itemList[m + 1] = list[t]


                    //collaps the empty item
                    var w = 1
                    while (w < list[t].duration) {
                        itemList[m + 1 + w] = getDefaultPlanner(999, 0, -1, 0)
                        w++
                    }
                }
                t++
            }
            m++
        }


// för varje positon i listan som är empty, kolla avstånd till närmsta filled item och ta countsen och spara de till planner object

        if (!list.isNullOrEmpty()) {
            var dummyStartTime: Int
            var biggestDummy: Int = -1

            for (i in itemList) {
                for (j in list) {
                    dummyStartTime = j.startTime


                    if (biggestDummy < j.startTime) {
                        biggestDummy = j.startTime
                    }

                    if (i.startTime < dummyStartTime) {
                        i.distanceToClosestFilledItem = dummyStartTime - i.startTime
                        break
                    }

                    if (i.startTime > biggestDummy && i.startTime < sleepTime) {
                        i.distanceToClosestFilledItem = sleepTime - i.startTime
                    }
                }

            }
        }

        return itemList
    }

    private fun getDefaultPlanner(
        startTime: Int,
        duration: Int,
        type: Int,
        distance: Int
    ): Planner {
        return Planner("", "", startTime, duration, type, distance, false)
    }

    fun calculateWakeUpTime(): LiveData<List<Int>> {
        var mutableList = MutableLiveData<List<Int>>()
        mutableList.value = calc()

        return mutableList
    }

    private fun calc(): List<Int> {
        var wakeupTime = 7
        var sleepTime = 22
        var index = 0
        var list: ArrayList<Int> = ArrayList()

        while (wakeupTime <= sleepTime) {
            list.add(index, wakeupTime)
            wakeupTime++
            index++
        }

        return list
    }
}