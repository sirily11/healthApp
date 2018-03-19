package com.example.qiweili.healthapp.health.Fragments

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.jjoe64.graphview.series.DataPoint
import com.jjoe64.graphview.series.LineGraphSeries

/**
 * Created by qiweili on 2018/3/18.
 */
class SectionPageAdapter(fm: FragmentManager?, val listSeries : MutableList<LineGraphSeries<DataPoint>>) : FragmentPagerAdapter(fm) {


    override fun getItem(position: Int): Fragment? {
        when(position){
            0 -> { return Tab1Fragment(listSeries[position])}
            1 -> return Tab2Fragment()
            2 -> return Tab3Fragment()
            else -> return null
        }
    }

    override fun getCount(): Int {
        return 3
    }

    override fun getPageTitle(position: Int): CharSequence? {
        when(position){
            0 -> return "Week"
            1 -> return "Month"
            2 -> return "Year"
            else -> return null
        }
    }
}