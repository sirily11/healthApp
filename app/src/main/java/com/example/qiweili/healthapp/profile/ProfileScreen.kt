package com.example.qiweili.healthapp.profile

import android.app.Activity
import android.os.Bundle
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.example.qiweili.healthapp.Drawer_menu
import com.example.qiweili.healthapp.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_profile_page.*
import kotlinx.android.synthetic.main.collapsing_toolbar.*
import kotlinx.android.synthetic.main.content_profile_page.*

class ProfileScreen : AppCompatActivity() {
    var myDrawerToggle: ActionBarDrawerToggle? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_page)
        setSupportActionBar(toolbar)
        //==========================================================================================

        val username = intent.getStringExtra("UserID")

        //========================Create a view layout management===================================

        supportActionBar?.setTitle("Profile")

        //========================Drawer menu=======================================================

        val drawer_layout = Drawer_menu(this, this@ProfileScreen, drawer_layout_profile, nav_Profile)
        myDrawerToggle = drawer_layout.mDrawerToggle
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        drawer_layout.setListener()

        //========================Drawer menu=======================================================

        about_me.setOnFocusChangeListener { view, hasFocus ->
            if (!hasFocus) {
                hideSoftKeyboard(view = view)
            }
        }

        //========================Firebase databse==================================================

        val userUID = FirebaseAuth.getInstance().currentUser?.uid
        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("$userUID/aboutme")
        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val value = dataSnapshot.getValue(String::class.java)
                about_me.setText(value)
            }
            override fun onCancelled(error: DatabaseError) {
                Log.w("Failed to read value.", error.toException())
            }
        })
        fab.setOnClickListener {

            view ->
            val aboutMe: String = about_me.text.toString()
            myRef.setValue(aboutMe)
            hideSoftKeyboard(view)
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
