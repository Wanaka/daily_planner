package com.example.jonas.daily_planner.model

data class Planner (val title: String, val description: String?, val startTime: Int, val duration: Int, val listPosition: Int = -1, val isNotificationEnabled: Boolean = false)