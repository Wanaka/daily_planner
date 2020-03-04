package com.example.jonas.daily_planner.view

import android.app.TimePickerDialog
import android.app.TimePickerDialog.OnTimeSetListener
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.example.jonas.daily_planner.R
import com.example.jonas.daily_planner.model.Planner
import com.example.jonas.daily_planner.model.WakeHoursModel
import com.example.jonas.daily_planner.view.PlannerActivity.*
import kotlinx.android.synthetic.main.time_picker_popup.*
import java.util.*

class TimePickerFragment : DialogFragment() {

    interface TimePickerInterface {
        fun timePickerData(wakeHours: WakeHoursModel)
    }

    lateinit var communicator: TimePickerInterface
    var hour = Calendar.getInstance().get(Calendar.HOUR)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        communicator = activity as TimePickerInterface

        return inflater.inflate(R.layout.time_picker_popup, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()

//        title = arguments?.get(PopUpFragment.GET_TITLE) as String

    }

    private fun init() {
        buttonStart.text = arguments?.get(GET_START_HOUR) as String
        buttonEnd.text = arguments?.get(GET_END_HOUR) as String

        buttonStart.setOnClickListener { getTime(true) }
        buttonEnd.setOnClickListener { getTime(false) }
        buttonSaveTime.setOnClickListener {
            communicator.timePickerData(
                WakeHoursModel(
                    buttonStart.text.toString(),
                    buttonEnd.text.toString()
                )
            )
            Toast.makeText(context, "Time saved", Toast.LENGTH_SHORT).show()
            dismiss()
        }
    }

    private fun getTime(isStartButton: Boolean) {
        TimePickerDialog(
            activity,
            OnTimeSetListener { _, h, _ ->
                if (isStartButton) buttonStart.text = h.toString()
                else buttonEnd.text = h.toString()
            },
            hour,
            0,
            true
        ).show()
    }

    companion object {
        const val GET_START_HOUR = "get_start_hour"
        const val GET_END_HOUR = "get_end_hour"

        fun newInstance(wakeHours: WakeHoursModel): TimePickerFragment {
            val timePickerFragment = TimePickerFragment()
            val bundle = Bundle()
            bundle.putString(GET_START_HOUR, wakeHours.startTime)
            bundle.putString(GET_END_HOUR, wakeHours.endTime)
            timePickerFragment.arguments = bundle
            return timePickerFragment
        }
    }
}