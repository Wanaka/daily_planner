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
        var plannerDummy: ArrayList<Planner> = ArrayList()
//        plannerDummy.add(Planner("Working", "My awesome project", 8, 3, 1, 0, false))
        plannerDummy.add(Planner("Working", "My awesome project", 19, 2, 1, 0, false))

        var itemList = arrayListOf<Planner>()

        //sätt in i funktion
        var e = 0
        while (e <= sleepTime - wakeupTime){

            if(e == 0){
                itemList.add(e, getDefaultPlanner(e, 1, 0, 0))
            } else {
                itemList.add(e, getDefaultPlanner(e + wakeupTime - 1, 1, -1, 0))
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
                        itemList[m + 1 + w] = getDefaultPlanner(999, 0, -1, 0)
                        w++
                    }
                }
                t++
            }
            m++
        }


// för varje positon i listan som är empty, kolla avstånd till närmsta filled item och ta countsen och spara de till planner object


        if(!plannerDummy.isNullOrEmpty()){

            var dummyStartTime: Int
            var biggestDummy: Int =-1

            for(i in itemList){
                for(j in plannerDummy){
                    dummyStartTime = j.startTime


                    if (biggestDummy < j.startTime){
                        biggestDummy = j.startTime
                    }

                    if(i.startTime < dummyStartTime && i.startTime != 0){

                        i.distanceToClosestFilledItem = dummyStartTime - i.startTime
//                        Log.d("MMM", "starttime less than dummy ${i.startTime}, dummy: $dummyStartTime, diff: ${dummyStartTime - i.startTime}")
                        Log.d("MMM", "i.distanceToClosestFilledItem ${ i.distanceToClosestFilledItem}")
                        break
                    }

                    if(i.startTime > biggestDummy && i.startTime < sleepTime){
                        i.distanceToClosestFilledItem = sleepTime - i.startTime

//                        Log.d("MMM", "starttime MORE than dummy ${i.startTime}, diff: ${sleepTime - i.startTime}")
                        Log.d("MMM", "i.distanceToClosestFilledItem BIGG ${ i.distanceToClosestFilledItem}")

                    }
                }

            }
        }
            // skapa funktion som kollar om det finns någon fylld item
        //om ej, räkna avstånd till slutet
        //om det finns, räkna avstånd till den första item efter klickta tomma item


        mutableList.value = itemList

        return mutableList
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
