package com.example.jonas.daily_planner.util

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Date

class Date {

    fun getDate(): String {
        var formatter: DateFormat = SimpleDateFormat("dd/MM/yyyy")

        var today = Date()

        var date = formatter.parse(formatter.format(today))

        return date.toString()
    }
}