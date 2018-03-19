package com.example.qiweili.healthapp.health.Fragments

import android.support.v4.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.qiweili.healthapp.R
import com.jjoe64.graphview.series.DataPoint
import com.jjoe64.graphview.series.LineGraphSeries
import kotlinx.android.synthetic.main.row_main_home.*

/**
 * Created by qiweili on 2018/3/18.
 */
class Tab1Fragment() : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.row_main_home_details,container,false)
        val data = arguments?.getDoubleArray("Week")
        return view
    }
}
class Tab2Fragment() : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.row_main_home_details2,container,false)
        val data = arguments?.getDoubleArray("Month")
        return view

    }
}

class Tab3Fragment() : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.row_main_home_details3,container,false)
        val data = arguments?.getDoubleArray("Year")
        return view

    }
}

