package com.example.jonas.daily_planner.ui

import androidx.lifecycle.*
import androidx.lifecycle.Observer
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.jonas.daily_planner.R
import com.example.jonas.daily_planner.base.BaseFragment
import com.example.jonas.daily_planner.di.DaggerAppComponent
import com.example.jonas.daily_planner.model.Planner
import com.example.jonas.daily_planner.navigator.NavigatorImpl
import com.example.jonas.daily_planner.ui.rv.PlannerAdapter
import kotlinx.android.synthetic.main.fragment_planer_list.*
import javax.inject.Inject

class PlannerListFragment : BaseFragment(), PlannerAdapter.OnItemClickListener {

    @Inject
    lateinit var factory: ViewModelProvider.Factory
    lateinit var plannerViewModel: PlannerViewModel

    @Inject
    lateinit var component: NavigatorImpl


    var item: Planner? = null


    override fun onAttach(context: Context?) {
         super.onAttach(context)

         DaggerAppComponent.create().inject(this)

         plannerViewModel = ViewModelProviders.of(this, factory).get(PlannerViewModel::class.java)
     }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        item = arguments?.get(KEY_POP_FRAGMENT_DATA) as? Planner

        if(item != null) sendToFiB(item!!)

        return inflater.inflate(R.layout.fragment_planer_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var itemList: List<Planner>

        //Remove laters
//        plannerViewModel. callWakeUpTime().observe(this, Observer {
//            numberRecyclerView.apply {
//                numberList = it!!
//
//                layoutManager = LinearLayoutManager(context)
//
//                adapter = TimeAdapter(numberList, context)
//            }
//        })

        plannerViewModel.callDummyData().observe(this, Observer {
            itemList = it!!

            itemRecyclerView.apply {
                layoutManager = LinearLayoutManager(context)

                adapter = PlannerAdapter(itemList,context, this@PlannerListFragment)
            }
        })
    }

    private fun sendToFiB(item: Planner){
        Log.d(",,,", "inputssss: ${item.title}, ${item.description}, ${item.startTime}, ${item.duration}")
    }

    override fun onItemClick(context: Context, item: Planner, position: Int) {
        component.newEvent(context, fragmentManager!!, item)
    }


    companion object {
        const val KEY_POP_FRAGMENT_DATA = "KEY_POP_FRAGMENT_DATA"
    }
}
