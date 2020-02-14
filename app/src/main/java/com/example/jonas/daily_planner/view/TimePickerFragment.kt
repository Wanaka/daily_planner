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
import kotlinx.android.synthetic.main.hour_popup.*
import java.util.*


class TimePickerFragment : DialogFragment() {

    var hour = Calendar.getInstance().get(Calendar.HOUR)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.hour_popup, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        buttonStart.text = hour.toString()
        buttonEnd.text = (hour + 1).toString()

        buttonStart.setOnClickListener { getTime(true) }
        buttonEnd.setOnClickListener { getTime(false) }
        buttonSaveTime.setOnClickListener {
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
        fun newInstance(): TimePickerFragment {
            return TimePickerFragment()
        }
    }
}