package com.example.jonas.daily_planner.ui

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.example.jonas.daily_planner.R
import com.example.jonas.daily_planner.base.BaseActivity
import com.example.jonas.daily_planner.model.Planner
import com.example.jonas.daily_planner.navigator.NavigatorImpl
import com.example.jonas.daily_planner.util.SharedPreferenceDate
import kotlinx.android.synthetic.main.custom_toolbar.*
import java.io.Serializable
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class PlannerActivity : BaseActivity(), PopUpFragment.Communicator {

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

    fun updatePlannerListFragment() {
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
        }
        return super.onOptionsItemSelected(item)
    }


    companion object {
        const val KEY_POP_FRAGMENT_DATA = "KEY_POP_FRAGMENT_DATA"
    }
}
