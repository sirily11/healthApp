package com.example.qiweili.healthapp.health.Fragments

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.example.qiweili.healthapp.DatabaseHelper
import com.example.qiweili.healthapp.health.Calculate_Cals
import com.example.qiweili.utils
import com.jjoe64.graphview.series.DataPoint
import com.jjoe64.graphview.series.LineGraphSeries

/**
 * Created by qiweili on 2018/3/18.
 */
class SectionPageAdapter(context : Context,fm: FragmentManager?) : FragmentPagerAdapter(fm) {

    val db = DatabaseHelper(context,utils.databaseName)
    override fun getItem(position: Int): Fragment? {
        when(position){
            0 -> {
                val bundle = Bundle()
                val food = db.getFood(utils.account_id!!)
                val cals = Calculate_Cals(food!!).getCals_intake()
                val array = mutableListOf<Double>()
                for(c in cals){
                    array.add(c.data!!.toDouble())
                }
                bundle.putDoubleArray("Week",array.toDoubleArray())
                val tab1 = Tab1Fragment()
                tab1.arguments = bundle
                return tab1
            }
            1 -> {
                val bundle = Bundle()
                bundle.putDoubleArray("Month",utils.randomDoubleListGenerator(0.0,2000.0,30).toDoubleArray())
                val tab2 = Tab2Fragment()
                tab2.arguments = bundle
                return tab2
            }
            2 -> {
                val bundle = Bundle()
                bundle.putDoubleArray("Year",utils.randomDoubleListGenerator(0.0,2000.0,365).toDoubleArray())
                val tab3 = Tab3Fragment()
                tab3.arguments = bundle
                return tab3
            }

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