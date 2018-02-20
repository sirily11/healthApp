package com.example.qiweili.healthapp

import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.MenuItem
import android.view.View
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_homescreen.*
import kotlinx.android.synthetic.main.collapsing_toolbar.*



class HomeScreen : AppCompatActivity() {
    //To create an drawerview toggle with optional type
    var myDrawerToggle: ActionBarDrawerToggle? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_homescreen)
        setSupportActionBar(toolbar)
        //------------------------------------------------------------------------------------------

        val user = FirebaseAuth.getInstance().currentUser?.email
        println("Welcome \n User name is $user")

        //Create a view layout management-----------------------------------------------------------
        DataView.layoutManager = LinearLayoutManager(DataView.context)
        windows_text.text = "Welcome $user"
        supportActionBar?.setTitle("Home")
        //-----------------------Drawer menu--------------------------------------------------------
        val drawer_layout = Drawer_menu(this, this@HomeScreen, drawer_layout_home,nav_Home )
        myDrawerToggle = drawer_layout.mDrawerToggle
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        drawer_layout.setListener()
        //-----------------------Drawer menu--------------------------------------------------------
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
