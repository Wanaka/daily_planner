package com.example.jonas.daily_planner.ui

import androidx.lifecycle.*
import androidx.lifecycle.Observer
import android.content.Context
import android.os.Bundle
import android.preference.PreferenceManager
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
import com.example.jonas.daily_planner.util.Key
import kotlinx.android.synthetic.main.fragment_planer_list.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

class PlannerListFragment: BaseFragment(), PlannerAdapter.OnItemClickListener {

    @Inject
    lateinit var factory: ViewModelProvider.Factory

    @Inject
    lateinit var plannerViewModel: PlannerViewModel

    @Inject
    lateinit var component: NavigatorImpl



    var item: Planner? = null


    override fun onAttach(context: Context?) {
         super.onAttach(context)
        DaggerAppComponent.create().inject(this)

        plannerViewModel = ViewModelProviders.of(this, factory).get(PlannerViewModel::class.java)
        Key(context!!).UUID()

     }

    override fun onStart() {
        super.onStart()
        Log.d(",,,", "onStart")
        CoroutineScope(IO).launch {
                        try {
                var t = plannerViewModel.getDataFromRepo(context!!).apply {
                    var u = plannerViewModel.get()
                    Log.d(",,,", "fragment data: $u")

                }
                withContext(Main){
                }

            } catch (e: Error) {
                Log.d("TAG", "Error: $e")
            }
        }


        }

    override fun onResume() {
        super.onResume()
        Log.d(",,,", "onResume")

    }

    override fun onPause() {
        super.onPause()
        Log.d(",,,", "onPause")

    }

    override fun onStop() {
        super.onStop()
        Log.d(",,,", "onStop")

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        item = arguments?.get(KEY_POP_FRAGMENT_DATA) as? Planner

        if(item != null) sendToFiB(item!!)

        return inflater.inflate(R.layout.fragment_planer_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var itemList: List<Planner>

        plannerViewModel.callDummyData().observe(this, Observer {
            itemList = it!!

            itemRecyclerView.apply {
                layoutManager = LinearLayoutManager(context)

                adapter = PlannerAdapter(itemList,context, this@PlannerListFragment)
            }
        })
    }

    private fun sendToFiB(item: Planner){
        CoroutineScope(IO).launch {
            try {
                plannerViewModel.sendItemToRepo(item, context!!)
            } catch (e: Error) {
                Log.d("TAG", "Error: $e")
            }
        }
    }

    override fun onItemClick(context: Context, item: Planner, position: Int) {
        component.newEvent(context, fragmentManager!!, item)
    }


    companion object {
        const val KEY_POP_FRAGMENT_DATA = "KEY_POP_FRAGMENT_DATA"
    }
}
