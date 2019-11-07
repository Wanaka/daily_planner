package com.example.jonas.daily_planner.ui

import android.app.AlertDialog
import android.arch.lifecycle.*
import android.arch.lifecycle.Observer
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.jonas.daily_planner.R
import com.example.jonas.daily_planner.di.DaggerMyComponent
import com.example.jonas.daily_planner.model.Planner
import com.example.jonas.daily_planner.navigator.NavigatorImpl
import com.example.jonas.daily_planner.ui.rv.PlannerAdapter
import com.example.jonas.daily_planner.ui.rv.TimeAdapter
import kotlinx.android.synthetic.main.fragment_planer_list.*
import javax.inject.Inject

class PlannerListFragment: Fragment(), PlannerAdapter.OnItemClickListener {

    @Inject
    lateinit var factory: ViewModelProvider.Factory
    lateinit var plannerViewModel: PlannerViewModel

    @Inject
    lateinit var component: NavigatorImpl


     override fun onAttach(context: Context?) {
         super.onAttach(context)

         DaggerMyComponent.create().inject(this)

         plannerViewModel = ViewModelProviders.of(this, factory).get(PlannerViewModel::class.java)
     }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_planer_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var itemList: List<Planner>
        var numberList: List<Int>

        plannerViewModel. callWakeUpTime().observe(this, Observer {
            numberRecyclerView.apply {
                numberList = it!!

                layoutManager = LinearLayoutManager(context)

                adapter = TimeAdapter(numberList, context)
            }
        })

        plannerViewModel.callDummyData().observe(this, Observer {
            itemList = it!!

            itemRecyclerView.apply {
                layoutManager = LinearLayoutManager(context)

                adapter = PlannerAdapter(itemList,context, this@PlannerListFragment)
            }
        })


    }

    override fun onItemClick(context: Context, item: Planner) {
        component.newEvent(context, item)

    }


}
