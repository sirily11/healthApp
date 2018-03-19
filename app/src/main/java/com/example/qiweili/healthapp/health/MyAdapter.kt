package com.example.qiweili.healthapp.health

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.qiweili.healthapp.pages.HomeScreen
import com.example.qiweili.healthapp.R
import kotlinx.android.synthetic.main.row_main_home.view.*


/**
 * This is the adapter for the recycle view
 * please implement all the methods below so that the view will work
 * as you want
 */

class MyAdapter(context: Context, data: List<HealthData>?) : RecyclerView.Adapter<HomeScreen.ViewHolder>() {
    /**
     * Private varibles
     */
    private var data = data
    private val myContext = context

    override fun getItemCount(): Int {
        val size : Int = if(data != null) data!!.size else 0
        return size
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): HomeScreen.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent?.context)
        val cellForRow = layoutInflater.inflate(R.layout.row_main_home, parent, false)
        return HomeScreen.ViewHolder(cellForRow)
    }

    override fun onBindViewHolder(holder: HomeScreen.ViewHolder?, position: Int) {
        holder?.view?.data?.text = data?.get(position)?.data?.toString()
        holder?.view?.description?.text = data?.get(position)?.description
    }
}
