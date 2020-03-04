package com.example.jonas.daily_planner.util

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.jonas.daily_planner.model.Planner
import com.example.jonas.daily_planner.model.WakeHoursModel

class TransformList {
    var wakeupTime = 0
    var sleepTime = 24

    var myTimeStart = 8
    var myTimeEnd = 10

    fun addPlannerDummy(list: ArrayList<Planner>, wakeHours: WakeHoursModel): ArrayList<Planner> {

        var itemList = arrayListOf<Planner>()
        myTimeStart = wakeHours.startTime.toInt()
        myTimeEnd = wakeHours.endTime.toInt()
        Log.d(",,,", "myTimeStart: ${myTimeStart}")

        //Start out with an entire collapsed list and then add empty spaces onto that list depending on the hours chosen.
        var e = 0
        while (e <= sleepTime - wakeupTime) {

            if (e == 0) {
                itemList.add(e, getDefaultPlanner(e, 1, 0, 0))
            } else {
                itemList.add(
                    e,
                    getDefaultPlanner(
                        999,
                        0,
                        -1,
                        0
                    )
                )
            }

            e++
        }

        //Add empty items during the wake hours decided
        while (myTimeStart <= myTimeEnd) {
            itemList[myTimeStart] =
                getDefaultPlanner(
                    myTimeStart,
                    1,
                    -1,
                    0
                )
            myTimeStart++
        }

        //Default list with empty items 0-24
//        var e = 0
//        while (e <= sleepTime - wakeupTime) {
//
//            if (e == 0) {
//                itemList.add(e, getDefaultPlanner(e, 1, 0, 0))
//            } else {
//                if (list.isNullOrEmpty()) {
//
//                    itemList.add(
//                        e,
//                        getDefaultPlanner(
//                            e + wakeupTime - 1,
//                            1,
//                            -1,
//                            (sleepTime - wakeupTime) - e + 1
//                        )
//                    )
//                }
//                else {
//                    itemList.add(e, getDefaultPlanner(e + wakeupTime - 1, 1, -1, 0))
//                }
//            }
//
//            e++
//        }

        // An invisible list item is needed otherwise the distance doesnt work properly
        list.add(Planner("", "", myTimeEnd + 1, 1, -2, 0, false))

        //Add items from firestore db
        var m = 0
        while (m <= sleepTime - wakeupTime) {
            var t = 0
            while (t < list.size) {
                if (calc()[m] == list[t].startTime) {
                    //add item to RV
                    itemList[m] = list[t]

                    //collaps the empty item
                    var w = 1
                    while (w < list[t].duration) {
                        itemList[m + w] = getDefaultPlanner(999, 0, -1, 0)
                        w++
                    }
                }
                t++
            }
            m++
        }


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

                if (i.startTime in (biggestDummy) until sleepTime) {
                    i.distanceToClosestFilledItem = sleepTime - i.startTime
                }
            }
        }

        // för varje positon i listan som är empty, kolla avstånd till närmsta filled item och ta countsen och spara de till planner objects
//        if (!list.isNullOrEmpty()) {
//            var dummyStartTime: Int
//            var biggestDummy: Int = -1
//
//            for (i in itemList) { //räknar igenom HELA listan, även collapsed items
//                for (j in list) {
//                    dummyStartTime = j.startTime
//
//
//                    if (biggestDummy < j.startTime) {
//                        biggestDummy = j.startTime
//                    }
//
//                    if (i.startTime < dummyStartTime) {
//                        i.distanceToClosestFilledItem = dummyStartTime - i.startTime
//                        break
//                    }
//
//                    if (i.startTime in (biggestDummy + 1) until sleepTime) {
//                        i.distanceToClosestFilledItem = sleepTime - i.startTime
//                    }
//                }
//
//            }
//        }

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
        var wakeupTime = 0
        var sleepTime = 24
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