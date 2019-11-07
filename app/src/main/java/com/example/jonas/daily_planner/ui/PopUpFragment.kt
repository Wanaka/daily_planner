package com.example.jonas.daily_planner.ui

import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.jonas.daily_planner.R
import com.example.jonas.daily_planner.di.DaggerMyComponent
import com.example.jonas.daily_planner.model.Planner

class PopUpFragment: DialogFragment() {


    override fun onAttach(context: Context?) {
        super.onAttach(context)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.item_popup, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    companion object {
        private val ARG_CAUGHT = "myFragment_caught"

        fun newInstance(item: Planner): PopUpFragment{


/*
            val args = Bundle()
            args.putSerializable(ARG_CAUGHT, item)

            val fragment = PopUpFragment()
            fragment.setArguments(args)


 */
            return PopUpFragment()
        }

    }

    }