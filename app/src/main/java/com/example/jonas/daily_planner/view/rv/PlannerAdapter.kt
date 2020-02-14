package com.example.jonas.daily_planner.view.rv

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.jonas.daily_planner.R
import com.example.jonas.daily_planner.model.Planner
import kotlinx.android.synthetic.main.planner_empty_list_item.view.*
import kotlinx.android.synthetic.main.planner_list_item.view.*
import kotlinx.android.synthetic.main.planner_list_item.view.list_item
import kotlinx.android.synthetic.main.planner_list_item.view.title


class PlannerAdapter(
    private val items: List<Planner>,
    private val context: Context,
    private val mListener: OnItemClickListener?
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    interface OnItemClickListener {
        fun onItemClick(context: Context, item: Planner, position: Int)
    }

    override fun getItemCount(): Int {
        return items.size
    }


    override fun getItemViewType(position: Int): Int {
        return when {
            items[position].itemType == 0 -> FIRST
            items[position].itemType == -1 -> EMPTY_ITEM
            else -> ITEM
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        var viewHolder: RecyclerView.ViewHolder

        when (viewType) {
            ITEM -> viewHolder = ItemViewHolder(
                LayoutInflater.from(context).inflate(
                    R.layout.planner_list_item,
                    parent,
                    false
                )
            )

            EMPTY_ITEM -> viewHolder = EmptyViewHolder(
                LayoutInflater.from(context).inflate(
                    R.layout.planner_empty_list_item,
                    parent,
                    false
                )
            )

            else -> viewHolder = WakeUpViewHolder(
                LayoutInflater.from(context).inflate(
                    R.layout.wake_up_item,
                    parent,
                    false
                )
            )
        }

        return viewHolder
    }

    // Binds each item in the ArrayList to a view
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == FIRST) {
            (holder as WakeUpViewHolder)
        } else if (getItemViewType(position) == ITEM) {
            (holder as ItemViewHolder).itemTitle.text = items[position].title
            holder.itemSubText.text = items[position].description
            holder.time.text = items[position].startTime.toString()
            holder.listItem.layoutParams.height *= items[position].duration

            holder.listItem.setOnClickListener {
                mListener!!.onItemClick(context, items[position], position)
            }
        } else {
            (holder as EmptyViewHolder)
            holder.listItem.layoutParams.height *= items[position].duration
            holder.timeEmpty.text = items[position].startTime.toString()
            if( items[position].startTime == 999) holder.timeEmpty.visibility = View.GONE

            holder.listItem.setOnClickListener {
                mListener!!.onItemClick(context, items[position], position)
            }
        }
    }

    companion object {
        private const val FIRST = 0
        private const val EMPTY_ITEM = 1
        private const val ITEM = 2
    }
}

class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val itemTitle: TextView = view.title
    val itemSubText: TextView = view.sub_text
    val time: TextView = view.time
    val listItem: View = view.list_item
}

class EmptyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val listItem: View = view.list_item
    val timeEmpty: TextView = view.timeEmpty
}

class WakeUpViewHolder(view: View) : RecyclerView.ViewHolder(view)