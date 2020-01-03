package com.example.jonas.daily_planner.repository

import android.content.Context
import android.text.format.DateUtils
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.jonas.daily_planner.model.Planner
import com.example.jonas.daily_planner.util.Date
import com.example.jonas.daily_planner.util.Key
import com.google.firebase.firestore.FirebaseFirestore
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.DayOfWeek
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

class FireStoreService @Inject constructor(var date: Date) {

    private val db = FirebaseFirestore.getInstance()
    var plannerList: ArrayList<Planner> = ArrayList()

    fun postToFireStore(item: Planner, context: Context){
        db.collection("users").document(Key(context).getUUID()).collection(date.getDate()).document(item.startTime.toString())
            .set(item)
            .addOnSuccessListener { Log.d(",,,", "DocumentSnapshot successfully written!") }
            .addOnFailureListener { e -> Log.w(",,,", "Error writing document", e) }
    }


fun get(): ArrayList<Planner> {
    Log.d(",,,", "GET: $plannerList")
    return plannerList
}
    fun getDataFromFireStore(context: Context){

        db.collection("users").document(Key(context).getUUID()).collection(date.getDate())
            .addSnapshotListener { snapshots, e ->
                if (snapshots != null) {
                    for (i in snapshots){

                        plannerList.add(
                            Planner(i.get("title").toString(),
                                i.get("description").toString(),
                                i.get("startTime").toString().toInt(),
                                i.get("duration").toString().toInt(),
                                i.get("itemType").toString().toInt(),  i.get("distanceToClosestFilledItem").toString().toInt(),
                                i.get("isNotificationEnabled").toString().toBoolean()
                            ))


                    }
                }
                Log.d(",,,", "fs data 1: ${plannerList}")

            }
        Log.d(",,,", "fs data 2: ${plannerList}")

//        plannerList.add(Planner("Working", "My awesome project", 19, 2, 1, 0, false))

    }


//    fun getDataFromFireStore(context: Context): ArrayList<Planner>{
//        var plannerList: ArrayList<Planner> = ArrayList()
//        db.collection("users").document(Key(context).getUUID()).collection(date.getDate())
//        .get()
//            .addOnSuccessListener { document ->
//                if (document != null) {
//                    for (i in document){
//
//                        plannerList.add(
//                            Planner(i.get("title").toString(),
//                            i.get("description").toString(),
//                            i.get("startTime").toString().toInt(),
//                            i.get("duration").toString().toInt(),
//                            i.get("itemType").toString().toInt(),  i.get("distanceToClosestFilledItem").toString().toInt(),
//                            i.get("isNotificationEnabled").toString().toBoolean()
//                            ))
//                    }
//                }
//
//            }
//            .addOnFailureListener { exception ->
//                Log.d(",,,", "get failed with ", exception)
//            }
//
//        return plannerList
//
//    }
}