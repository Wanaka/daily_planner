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

class PopUpFragment : DialogFragment() {

    lateinit var communicator: Communicator
    private var start = 0
    private var hour = 0
    private var title = ""
    private var subTitle = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        communicator = activity as Communicator
        return inflater.inflate(R.layout.item_popup, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        start = arguments?.get(GET_STARTING_TIME) as Int
        hour = arguments?.get(GET_HOURS) as Int
        title = arguments?.get(GET_TITLE) as String
        subTitle = arguments?.get(GET_SUB_TITLE) as String

        input_title.setText(title)
        input_sub_title.setText(subTitle)
        starting_time.text = start.toString()
        hours.text = hour.toString()
        slider.max = hour
        slider.progress = hour

        slider()
        saveButtonClick()
        close()
    }

    private fun slider() {
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

    private fun saveButtonClick() {
        save_item.setOnClickListener {
            try {
                communicator.passDataFromPopFragmentToPlannerFragment(
                    Planner(
                        input_title.text.toString(),
                        input_sub_title.text.toString(),
                        start,
                        hours.text.toString().toInt(),
                        1,
                        0,
                        false
                    )
                )
                dismiss()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun close() {
        close.setOnClickListener { dismiss() }
    }

    interface Communicator {
        fun passDataFromPopFragmentToPlannerFragment(item: Planner)
    }

    companion object {
        const val GET_TITLE = "key_title"
        const val GET_SUB_TITLE = "key__sub_title"
        const val GET_STARTING_TIME = "key_start_time"
        const val GET_HOURS = "key_hours"

        fun newInstance(item: Planner): PopUpFragment {
            val popUpFragment = PopUpFragment()
            val bundle = Bundle()
            bundle.putString(GET_TITLE, item.title)
            bundle.putString(GET_SUB_TITLE, item.description)
            bundle.putInt(GET_STARTING_TIME, item.startTime)
            bundle.putInt(GET_HOURS, item.distanceToClosestFilledItem)
            popUpFragment.arguments = bundle
            return popUpFragment
        }
    }
}