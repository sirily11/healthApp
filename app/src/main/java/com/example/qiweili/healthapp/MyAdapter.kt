package com.example.qiweili.healthapp

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.row_main_friend.view.*


/**
 * This is the adapter for the recycle view
 * please implement all the methods below so that the view will work
 * as you want
 */

class MyAdapter(context: Context, data: List<DataAPI>) : RecyclerView.Adapter<HomeScreen.ViewHolder>() {
    /**
     * Private varibles
     */
    private var data = data
    private val myContext = context

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): HomeScreen.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent?.context)
        val cellForRow = layoutInflater.inflate(R.layout.row_main_friend, parent, false)
        return HomeScreen.ViewHolder(cellForRow)
    }

    override fun onBindViewHolder(holder: HomeScreen.ViewHolder?, position: Int) {
        holder?.view?.first_name?.text = data.get(position).first_name
        holder?.view?.last_name?.text = data.get(position).last_name
        val imageView = holder?.view?.imageView_profile
        Picasso.with(myContext)
                .load(data.get(position).img_url)
                .into(imageView)

    }
}
