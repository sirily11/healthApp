package com.example.qiweili.healthapp.health.Fragments

import android.support.v4.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.qiweili.healthapp.R
import com.example.qiweili.healthapp.health.GraphView
import com.jjoe64.graphview.series.DataPoint
import com.jjoe64.graphview.series.LineGraphSeries
import kotlinx.android.synthetic.main.row_main_home.*
import kotlinx.android.synthetic.main.row_main_home_details.*
import kotlinx.android.synthetic.main.row_main_home_details.view.*
import kotlinx.android.synthetic.main.row_main_home_details2.view.*
import kotlinx.android.synthetic.main.row_main_home_details3.*
import kotlinx.android.synthetic.main.row_main_home_details3.view.*

/**
 * Created by qiweili on 2018/3/18.
 */
class Tab1Fragment() : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.row_main_home_details,container,false)
        val data = arguments?.getDoubleArray("Week")
        val graph = GraphView(data!!.toMutableList(),1.0)
        graph.series?.isDrawAsPath = true
        graph.series2?.isAnimated = true
        view.graphView.addSeries(graph.series2)
        view.graphView.addSeries(graph.series)
        view.graphView.viewport.setMaxX(7.0)
        view.graphView.viewport.isScalable = true
        return view
    }
}
class Tab2Fragment() : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.row_main_home_details2,container,false)
        val data = arguments?.getDoubleArray("Month")
        val graph = GraphView(data!!.toMutableList(),1.0)
        graph.series?.isDrawAsPath = true
        graph.series2?.isAnimated = true
        view.graphView2.addSeries(graph.series2)
        view.graphView2.addSeries(graph.series)
        view.graphView2.viewport.setMaxX(30.0)
        view.graphView2.viewport.isScalable = true

        return view

    }
}

class Tab3Fragment() : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.row_main_home_details3,container,false)
        val data = arguments?.getDoubleArray("Year")
        val graph = GraphView(data!!.toMutableList(),20.0)
        graph.series?.isDrawAsPath = true
        graph.series2?.isAnimated = true
        view.graphView3.addSeries(graph.series2)
        view.graphView3.addSeries(graph.series)
        view.graphView3.viewport.setMaxX(365.0)
        view.graphView3.viewport.isScalable = true
        return view

    }
}

