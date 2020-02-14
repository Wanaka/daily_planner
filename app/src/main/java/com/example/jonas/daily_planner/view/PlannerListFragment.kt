package com.example.jonas.daily_planner.view

import androidx.lifecycle.*
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.jonas.daily_planner.R
import com.example.jonas.daily_planner.base.BaseFragment
import com.example.jonas.daily_planner.di.DaggerAppComponent
import com.example.jonas.daily_planner.model.Planner
import com.example.jonas.daily_planner.navigator.NavigatorImpl
import com.example.jonas.daily_planner.view.rv.PlannerAdapter
import com.example.jonas.daily_planner.view.rv.PlannerAdapter.*
import com.example.jonas.daily_planner.util.Key
import com.example.jonas.daily_planner.util.SharedPreferenceDate
import kotlinx.android.synthetic.main.fragment_planer_list.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PlannerListFragment : BaseFragment(), OnItemClickListener {

    @Inject
    lateinit var factory: ViewModelProvider.Factory

    @Inject
    lateinit var plannerViewModel: PlannerViewModel

    @Inject
    lateinit var component: NavigatorImpl

    @Inject
    lateinit var sharedPref: SharedPreferenceDate

    private lateinit var list: ArrayList<Planner>
    var item: Planner? = null
    private lateinit var getDate: String


    override fun onAttach(context: Context?) {
        super.onAttach(context)
        DaggerAppComponent.create().inject(this)
        plannerViewModel = ViewModelProviders.of(this, factory).get(PlannerViewModel::class.java)
        Key(context!!).UUID()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        getDate = sharedPref.getDate(context!!)
        item = arguments?.get(KEY_POP_FRAGMENT_DATA) as? Planner

        //send date to firestore
        if (item != null) sendToFireStore(item!!)
        getList()

        return inflater.inflate(R.layout.fragment_planer_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getList()
        setRecyclerViewItemTouchListener()
    }

    private fun getList() {
        CoroutineScope(IO).launch {
            list = plannerViewModel.getDataFromRepo(context!!, getDate)

            withContext(Main) {
                itemRecyclerView.apply {
                    layoutManager = LinearLayoutManager(context)
                    adapter = PlannerAdapter(list, context, this@PlannerListFragment)
                }
            }
        }
    }

    private fun sendToFireStore(item: Planner) {
        CoroutineScope(IO).launch {
            try {
                plannerViewModel.sendItemToRepo(item, context!!, getDate)
            } catch (e: Error) {
                Log.d("TAG", "Error: $e")
            }
        }
    }

    override fun onItemClick(context: Context, item: Planner, position: Int) {
        component.newEvent(context, fragmentManager!!, item)
    }

    private fun setRecyclerViewItemTouchListener() {
        val itemTouchCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {

            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                viewHolder1: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, swipeDir: Int) {
                CoroutineScope(IO).launch {
                    try {
                        plannerViewModel.deleteItems(
                            list[viewHolder.adapterPosition].startTime.toString(),
                            context!!,
                            getDate
                        )
                    } catch (e: Error) {
                    }
                }

                getList()
            }
        }

        val itemTouchHelper = ItemTouchHelper(itemTouchCallback)
        itemTouchHelper.attachToRecyclerView(itemRecyclerView)
    }

    companion object {
        const val KEY_POP_FRAGMENT_DATA = "KEY_POP_FRAGMENT_DATA"
    }
}
