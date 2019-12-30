package com.example.jonas.daily_planner.repository

import android.util.Log
import com.example.jonas.daily_planner.model.Planner
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*
import javax.inject.Inject

class FireStoreService @Inject constructor() {

    private val db = FirebaseFirestore.getInstance()

    fun postToFireStore(item: Planner){
        db.collection(UUID.randomUUID().toString()).document(Date().toString()).collection(item.startTime.toString()).document()
            .set(item)
            .addOnSuccessListener { Log.d(",,,", "DocumentSnapshot successfully written!") }
            .addOnFailureListener { e -> Log.w(",,,", "Error writing document", e) }
    }
}