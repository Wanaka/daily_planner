package com.example.jonas.daily_planner.model

import java.io.Serializable

data class Planner (val title: String, val description: String?, val startTime: Int, val duration: Int, val itemType: Int, var distanceToClosestFilledItem: Int, val isNotificationEnabled: Boolean = false) :
    Serializable