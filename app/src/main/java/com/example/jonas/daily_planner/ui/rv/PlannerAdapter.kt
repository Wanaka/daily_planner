package com.example.jonas.daily_planner.ui.rv

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.DisplayMetrics
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.TextView
import android.widget.Toast
import com.example.jonas.daily_planner.R
import com.example.jonas.daily_planner.model.Planner
import kotlinx.android.synthetic.main.planner_empty_list_item.view.*
import kotlinx.android.synthetic.main.planner_list_item.view.*
import kotlinx.android.synthetic.main.planner_list_item.view.list_item



class PlannerAdapter(private val items : List<Planner>, private val context: Context, private val mListener: OnItemClickListener?) : RecyclerView.Adapter<RecyclerView.ViewHolder>(), View.OnClickListener {


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
        if(getItemViewType(position) == FIRST){
            (holder as WakeUpViewHolder)
        } else if (getItemViewType(position) == ITEM) {
            (holder as ItemViewHolder).itemTitle.text = items[position].title
            holder.listItem.layoutParams.height *= items[position].duration
        } else {
            (holder as EmptyViewHolder)
            holder.listItem.layoutParams.height *= items[position].duration
            //holder.textt.text_.text = items[position].title
                holder.listItem.setOnClickListener {
                    mListener!!.onItemClick(context, items[position], position)
                }
        }
    }

    override fun onClick(p0: View?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    companion object {
        private const val FIRST = 0
        private const val EMPTY_ITEM = 1
        private const val ITEM = 2
    }
}

class ItemViewHolder (view: View) : RecyclerView.ViewHolder(view) {
    // Holds the TextView that will add each animal to
    val itemTitle: TextView = view.text_wake_up
    val listItem: View = view.list_item
}

class EmptyViewHolder (view: View) : RecyclerView.ViewHolder(view){
    val listItem: View = view.list_item
    //val textt: View = view.text_

}
class WakeUpViewHolder (view: View) : RecyclerView.ViewHolder(view)