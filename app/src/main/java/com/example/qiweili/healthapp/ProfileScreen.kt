package com.example.qiweili.healthapp

import android.app.Activity
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageButton
import kotlinx.android.synthetic.main.activity_homescreen.*

import kotlinx.android.synthetic.main.activity_profile_page.*
import kotlinx.android.synthetic.main.collapsing_toolbar.*
import kotlinx.android.synthetic.main.content_profile_page.*

class ProfileScreen : AppCompatActivity() {
    var myDrawerToggle: ActionBarDrawerToggle? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_page)
        setSupportActionBar(toolbar)
        //------------------------------------------------------------------------------------------
        val username = intent.getStringExtra("UserID")

        //Create a view layout management-----------------------------------------------------------

        supportActionBar?.setTitle("Home")
        //-----------------------Drawer menu--------------------------------------------------------
        val drawer_layout = Drawer_menu(this, this@ProfileScreen, drawer_layout_profile,nav_Profile )
        myDrawerToggle = drawer_layout.mDrawerToggle
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        drawer_layout.setListener()
        //-----------------------Drawer menu--------------------------------------------------------
        about_me.setOnFocusChangeListener{
            view, hasFocus ->
            if (!hasFocus) {
                hideSoftKeyboard(view = view)
            }
        }





    }
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        println("Item is $item")
        if (myDrawerToggle?.onOptionsItemSelected(item)!!) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    fun hideSoftKeyboard(view: View) {
        val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0)
    }
}
