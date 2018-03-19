package com.example.qiweili.healthapp.health

import android.support.v7.app.AppCompatActivity

import android.support.v4.view.ViewPager
import android.os.Bundle
import android.support.v4.app.Fragment

import com.example.qiweili.healthapp.R
import com.example.qiweili.healthapp.health.Fragments.SectionPageAdapter
import com.example.qiweili.healthapp.health.Fragments.Tab1Fragment
import com.example.qiweili.healthapp.health.Fragments.Tab2Fragment
import com.example.qiweili.healthapp.health.Fragments.Tab3Fragment
import kotlinx.android.synthetic.main.activity_home_details.*
import kotlinx.android.synthetic.main.row_main_home_details.*


class home_details : AppCompatActivity() {
    var mSectionsPageAdapter : SectionPageAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_details)
        setSupportActionBar(toolbar)
        val graph = GraphView(mutableListOf(2.0,3.0,4.0,10.0))
        createTab()

    }

    private fun createTab(){
        tab_layout.addTab(tab_layout.newTab().setText("Week"))
        tab_layout.addTab(tab_layout.newTab().setText("Month"))
        tab_layout.addTab(tab_layout.newTab().setText("Year"))
        mSectionsPageAdapter = SectionPageAdapter(supportFragmentManager)
        pager.adapter = mSectionsPageAdapter
        tab_layout.setupWithViewPager(pager)
    }
}