package com.example.jonas.daily_planner.ui

import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.Toast
import com.example.jonas.daily_planner.R
import com.example.jonas.daily_planner.di.DaggerMyComponent
import com.example.jonas.daily_planner.model.Planner
import kotlinx.android.synthetic.main.item_popup.*

class PopUpFragment: DialogFragment() {

    override fun onAttach(context: Context?) {
        super.onAttach(context)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.item_popup, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        text_start_time.text = this!!.arguments!![KEY_START_TIME].toString()
        slider_end_time.max = this!!.arguments!![KEY_HOURS] as Int
        slider_end_time.min = 0
        slider_end_time.progress = this!!.arguments!![KEY_HOURS] as Int
        text_hours.text = this!!.arguments!![KEY_HOURS].toString()

        slider_end_time.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {

            override fun onProgressChanged(seekBar: SeekBar, i: Int, b: Boolean) {
                // Display the current progress of SeekBar
                slider_end_time.setProgress(i, true)
                text_hours.text = i.toString()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {}

            override fun onStopTrackingTouch(seekBar: SeekBar) {
                if (seekBar.progress == 0) seekBar.progress = 1
            }
        })
    }

    companion object {
        const val KEY_START_TIME = "key_start_time"
        const val KEY_HOURS = "key_hours"

        fun newInstance(item: Planner): PopUpFragment{
            val frag = PopUpFragment()
            val bundle = Bundle()
            bundle.putInt(KEY_START_TIME, item.startTime)
            bundle.putInt(KEY_HOURS, item.distanceToClosestFilledItem)
            frag.arguments = bundle
            return frag
        }
    }
}