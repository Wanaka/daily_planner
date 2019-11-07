package com.example.jonas.daily_planner.ui.rv

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.jonas.daily_planner.R
import com.example.jonas.daily_planner.model.Planner
import kotlinx.android.synthetic.main.number_item.view.*
import kotlinx.android.synthetic.main.planner_list_item.view.*

class TimeAdapter(private val items : List<Int>, private val context: Context) : RecyclerView.Adapter<TimeViewHolder>() {

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TimeViewHolder {
        return TimeViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.number_item,
                parent,
                false
            )
        )
    }

    // Binds each animal in the ArrayList to a view
    override fun onBindViewHolder(holder: TimeViewHolder, position: Int) {
        holder.itemNumber.text = items[position].toString()
    }
}

class TimeViewHolder (view: View) : RecyclerView.ViewHolder(view) {
    // Holds the TextView that will add each animal to
    val itemNumber: TextView = view.text_number
}