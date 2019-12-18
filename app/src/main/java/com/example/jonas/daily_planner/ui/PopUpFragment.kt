package com.example.jonas.daily_planner.ui

import android.os.Bundle
import androidx.fragment.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import com.example.jonas.daily_planner.R
import com.example.jonas.daily_planner.model.Planner
import kotlinx.android.synthetic.main.item_popup.*

class PopUpFragment: DialogFragment() {

    interface Communicator {
        fun passDataFromPopFragmentToPlannerFragment(editext_input: String)
    }

    lateinit var communicator: Communicator

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        communicator = activity as Communicator
        return inflater.inflate(R.layout.item_popup, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        starting_time.text = arguments?.get(GET_STARTING_TIME).toString()
        hours.text = arguments?.get(GET_HOURS).toString()
        slider.max = arguments?.get(GET_HOURS) as Int
        slider.progress = arguments?.get(GET_HOURS) as Int

        slider()
        saveButtonClick()
    }

    private fun slider(){
        slider.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {

            override fun onProgressChanged(seekBar: SeekBar, i: Int, b: Boolean) {
                slider.setProgress(i, true)
                hours.text = i.toString()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {}

            override fun onStopTrackingTouch(seekBar: SeekBar) {
                if (seekBar.progress == 0) seekBar.progress = 1
            }

        })
    }

    private fun saveButtonClick(){
        save_item.setOnClickListener {
            try {
                communicator.passDataFromPopFragmentToPlannerFragment(input_title.text.toString())
                dismiss()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }


    companion object {
        const val GET_STARTING_TIME = "key_start_time"
        const val GET_HOURS = "key_hours"

        fun newInstance(item: Planner): PopUpFragment{
            val popUpFragment = PopUpFragment()
            val bundle = Bundle()
            bundle.putInt(GET_STARTING_TIME, item.startTime)
            bundle.putInt(GET_HOURS, item.distanceToClosestFilledItem)
            popUpFragment.arguments = bundle
            return popUpFragment
        }
    }
}