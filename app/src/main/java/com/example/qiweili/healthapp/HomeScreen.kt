package com.example.qiweili.healthapp

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Menu
import android.view.MenuItem
import android.view.View
import kotlinx.android.synthetic.main.activity_friend_screen.*
import kotlinx.android.synthetic.main.activity_homescreen.*


class HomeScreen : AppCompatActivity(){
    //To create an drawerview toggle with optional type
    var myDrawerToggle: ActionBarDrawerToggle? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_homescreen)

        //Create a view layout management
        DataView.layoutManager = LinearLayoutManager(DataView.context)

        //initialize the drawerview toggle object
        val drawer_layout = Drawer_menu(this,this@HomeScreen,drawer_layout_home,nav_home)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        myDrawerToggle = drawer_layout.mDrawerToggle
        drawer_layout.setListener()
    }
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        println("Item is $item")
        if (myDrawerToggle?.onOptionsItemSelected(item)!!) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }
    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

    }
}
