package com.example.jonas.daily_planner.repository

import android.content.Context
import android.text.format.DateUtils
import android.util.Log
import com.example.jonas.daily_planner.model.Planner
import com.example.jonas.daily_planner.util.Date
import com.example.jonas.daily_planner.util.Key
import com.google.firebase.firestore.FirebaseFirestore
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.DayOfWeek
import java.util.*
import javax.inject.Inject

class FireStoreService @Inject constructor(var date: Date) {

    private val db = FirebaseFirestore.getInstance()

    fun postToFireStore(item: Planner, context: Context){
        val key = Key(context)

        db.collection(key.getUUID()).document(date.getDate()).collection(item.startTime.toString()).document(item.startTime.toString())
            .set(item)
            .addOnSuccessListener { Log.d(",,,", "DocumentSnapshot successfully written!") }
            .addOnFailureListener { e -> Log.w(",,,", "Error writing document", e) }
    }
}