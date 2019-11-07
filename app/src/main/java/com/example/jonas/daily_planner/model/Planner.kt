package com.example.jonas.daily_planner.model

data class Planner (val title: String, val description: String?, val startTime: Int, val duration: Int, val itemType: Int, val isNotificationEnabled: Boolean = false)