package com.example.jonas.daily_planner.view

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import com.example.jonas.daily_planner.R
import com.example.jonas.daily_planner.base.BaseActivity
import com.example.jonas.daily_planner.di.DaggerAppComponent
import com.example.jonas.daily_planner.model.Planner
import com.example.jonas.daily_planner.model.WakeHoursModel
import com.example.jonas.daily_planner.navigator.NavigatorImpl
import com.example.jonas.daily_planner.util.SharedPreferenceDate
import com.example.jonas.daily_planner.view.PopUpFragment.*
import com.example.jonas.daily_planner.view.TimePickerFragment.*
import kotlinx.android.synthetic.main.custom_toolbar.*
import java.io.Serializable
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class PlannerActivity : BaseActivity(), Communicator, TimePickerInterface {

    @Inject
    lateinit var component: NavigatorImpl

    @Inject
    lateinit var sharedPref: SharedPreferenceDate

    private var cal: Calendar = Calendar.getInstance()
    private lateinit var dateSetListener: DatePickerDialog.OnDateSetListener


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_planner)
        setSupportActionBar(custom_toolbar)
        DaggerAppComponent.create().inject(this)

        sharedPref = SharedPreferenceDate()


        updateDateInView()
        datePicker()

        supportFragmentManager.beginTransaction()
            .add(R.id.fragment_container, PlannerListFragment())
            .commit()
    }

    private fun datePicker() {
        dateSetListener =
            DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, monthOfYear)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                updateDateInView()
                updatePlannerListFragment()
            }
    }

    private fun updateDateInView() {
        var dayDate = getDateFormat("dd")
        var day = getDateFormat("EEEE")
        var month = getDateFormat("MMMM")
        var year = getDateFormat("yyyy")

        supportActionBar!!.title = "$day $dayDate"
        supportActionBar!!.subtitle = "$month - $year"

        sharedPref.saveDate(this, "$dayDate$month$year")
    }

    private fun updatePlannerListFragment() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, PlannerListFragment())
            .commit()
    }

    private fun getDateFormat(format: String): String {
        return SimpleDateFormat(format, Locale.US).format(cal.time)
    }

    override fun passDataFromPopFragmentToPlannerFragment(item: Planner) {
        val bundle = Bundle()
        bundle.putSerializable(KEY_POP_FRAGMENT_DATA, item as Serializable)

        val passData = PlannerListFragment()
        passData.arguments = bundle

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, passData)
            .commit()
    }

    override fun timePickerData(q: WakeHoursModel) {
        val bundle = Bundle()
        bundle.putSerializable("Time_Picker_Data", q as Serializable)

        val passData = PlannerListFragment()
        passData.arguments = bundle

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, passData)
            .commit()
    }

    // Menu
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.toolbar_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_calendar -> {
                DatePickerDialog(
                    this,
                    dateSetListener,
                    cal.get(Calendar.YEAR),
                    cal.get(Calendar.MONTH),
                    cal.get(Calendar.DAY_OF_MONTH)
                ).show()
                return true
            }

            R.id.action_set_hour_range -> {
                component.openHourPopup(supportFragmentManager)
                return true
            }

        }
        return super.onOptionsItemSelected(item)
    }


    companion object {
        const val KEY_POP_FRAGMENT_DATA = "KEY_POP_FRAGMENT_DATA"
    }


}
