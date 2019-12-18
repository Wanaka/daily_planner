package com.example.jonas.daily_planner.ui

import androidx.lifecycle.*
import androidx.lifecycle.Observer
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.jonas.daily_planner.R
import com.example.jonas.daily_planner.base.BaseFragment
import com.example.jonas.daily_planner.di.DaggerAppComponent
import com.example.jonas.daily_planner.model.Planner
import com.example.jonas.daily_planner.navigator.NavigatorImpl
import com.example.jonas.daily_planner.repository.Repository
import com.example.jonas.daily_planner.ui.rv.PlannerAdapter
import kotlinx.android.synthetic.main.fragment_planer_list.*
import kotlinx.android.synthetic.main.item_popup.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.ClassCastException
import javax.inject.Inject

class PlannerListFragment : BaseFragment(), PlannerAdapter.OnItemClickListener {

    @Inject
    lateinit var factory: ViewModelProvider.Factory
    lateinit var plannerViewModel: PlannerViewModel

    @Inject
    lateinit var component: NavigatorImpl


    var inputText: String? = ""


    override fun onAttach(context: Context?) {
         super.onAttach(context)

         DaggerAppComponent.create().inject(this)

         plannerViewModel = ViewModelProviders.of(this, factory).get(PlannerViewModel::class.java)
     }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        inputText = arguments?.getString(KEY_POP_FRAGMENT_DATA)

        if(inputText != null) sendToFiB(inputText!!)

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

    private fun sendToFiB(send: String){
        Log.d(",,,", "inputssss: $send")
    }

    override fun onItemClick(context: Context, item: Planner, position: Int) {
        component.newEvent(context, fragmentManager!!, item)
    }


    companion object {
        const val KEY_POP_FRAGMENT_DATA = "KEY_POP_FRAGMENT_DATA"
    }
}
