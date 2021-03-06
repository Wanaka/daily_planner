package com.example.jonas.daily_planner.view

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.jonas.daily_planner.model.Planner
import com.example.jonas.daily_planner.model.WakeHoursModel
import com.example.jonas.daily_planner.repository.Repository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class PlannerViewModel @Inject constructor(var component: Repository): ViewModel() {
    //create a new Job
    private val parentJob = Job()
    //create a coroutine context with the job and the dispatcher
    private val coroutineContext : CoroutineContext get() = parentJob + Dispatchers.Default
    //create a coroutine scope with the coroutine context
    private val scope = CoroutineScope(coroutineContext)


//    fun callDummyData(): LiveData<List<Planner>> {
//        return component.addPlannerDummy()
//    }

    suspend fun sendItemToRepo(item: Planner, context: Context, activeDate: String){
        return component.postItemToFiB(item, context, activeDate)
    }

    suspend fun sendwakeHoursToRepo(wakeHours: WakeHoursModel, context: Context, activeDate: String) {
        return component.postwakeHoursToFiB(wakeHours, context, activeDate)
    }

    suspend fun getwakeHoursToRepo(context: Context, activeDate: String): WakeHoursModel {
        return component.getwakeHoursToFiB(context, activeDate)
    }

    suspend fun deleteItems(number: String, context: Context, activeDate: String) {
        return component.deleteItems(number, context, activeDate)
    }

//    suspend fun get(): List<Planner> {
//        return component.get()
//    }

    suspend fun getDataFromRepo(context: Context, getDate: String, wakeHours: WakeHoursModel): ArrayList<Planner> {
        return component.getDataFromFireStore(context, getDate, wakeHours)
    }

    fun callWakeUpTime(): LiveData<List<Int>>{
        return component.calculateWakeUpTime()
    }
}