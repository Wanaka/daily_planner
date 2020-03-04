package com.example.jonas.daily_planner.repository

import android.content.Context
import android.util.Log
import com.example.jonas.daily_planner.model.Planner
import com.example.jonas.daily_planner.model.WakeHoursModel
import com.example.jonas.daily_planner.util.Key
import com.example.jonas.daily_planner.util.TransformList
import com.google.android.gms.tasks.Tasks
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QueryDocumentSnapshot
import javax.inject.Inject
import kotlin.collections.ArrayList

class FireStoreService @Inject constructor() {
    private val db = FirebaseFirestore.getInstance()


    fun postToFireStore(item: Planner, context: Context, activeDate: String) {
        db.collection("users").document(Key(context).getUUID()).collection(activeDate)
            .document(item.startTime.toString())
            .set(item)
            .addOnSuccessListener { Log.d(",,,", "DocumentSnapshot successfully written!") }
            .addOnFailureListener { e -> Log.w(",,,", "Error writing document", e) }
    }


    fun postWakeHours(wakeHours: WakeHoursModel, context: Context, activeDate: String) {
        db.collection("users").document(Key(context).getUUID()).collection(activeDate + "wakeHours")
            .document("hours")
            .set(wakeHours)
            .addOnSuccessListener { Log.d(",,,", "DocumentSnapshot successfully written!") }
            .addOnFailureListener { e -> Log.w(",,,", "Error writing document", e) }
    }


    suspend fun getWakeHours(context: Context, activeDate: String): WakeHoursModel {
        var startTime = ""
        var endTime = ""

        val path = db.collection("users").document(Key(context).getUUID())
            .collection(activeDate + "wakeHours")
        var document = Tasks.await(path.get())

        if (document.documents != null) {
            for (i in document.documents) {
                startTime = i["startTime"].toString()
                endTime = i["endTime"].toString()
            }
        }
        Log.d(",,,,", "repo: ${startTime}")

        return WakeHoursModel(startTime, endTime)
    }


    fun deleteItems(number: String, context: Context, activeDate: String) {
        db.collection("users").document(Key(context).getUUID()).collection(activeDate)
            .document(number)
            .delete()
            .addOnSuccessListener { Log.d(",,,", "DocumentSnapshot successfully deleted!") }
            .addOnFailureListener { e -> Log.w(",,,", "Error writing document", e) }
    }


    suspend fun getDataFromFireStore(context: Context, getDate: String, wakeHours: WakeHoursModel): ArrayList<Planner> {

        Log.d(",,,", "wakehours firebasedb: $wakeHours")
        var plannerList: ArrayList<Planner> = ArrayList()

        return try {
            var path =
                db.collection("users").document(Key(context).getUUID()).collection(getDate)
            var document = Tasks.await(path.get())

            if (document.documents != null) {
                for (i in document.documents) {
                    Log.d(",,,", "Getlist")
                    plannerList.add(
                        Planner(
                            i.get("title").toString(),
                            i.get("description").toString(),
                            i.get("startTime").toString().toInt(),
                            i.get("duration").toString().toInt(),
                            i.get("itemType").toString().toInt(),
                            i.get("distanceToClosestFilledItem").toString().toInt(),
                            i.get("isNotificationEnabled").toString().toBoolean()
                        )
                    )
                }
            }
            var t = TransformList()

            t.addPlannerDummy(plannerList, wakeHours)
        } finally {

        }
    }
}