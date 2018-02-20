package com.example.qiweili.healthapp

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.support.design.widget.NavigationView
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.Menu

/**
 *This is the drawer_menu class which is a class to
 * Set up all the redundant code to set up a drawer menu
 */
class Drawer_menu(context:Context, activity:Activity,
                  drawerLayout: DrawerLayout,navigationView: NavigationView) : AppCompatActivity() {
    /**
     * Set up some variables
     */
    var mContext = context
    var mActivity = activity
    var mDrawerToggle:ActionBarDrawerToggle? = null
    var mDrawerLayout = drawerLayout
    var mNavigationView = navigationView

    /**
     * Initialize the action bar and drawer listener
     */
    init {

        mDrawerToggle = ActionBarDrawerToggle(mActivity, drawerLayout,
                R.string.common_open_on_phone, R.string.mr_controller_close_description)
        //add the toggle object
        drawerLayout.addDrawerListener(mDrawerToggle!!)
        mDrawerToggle?.syncState()
    }
    /**
     * Helper method for nav menu
     */
    private fun goto(f: () -> Unit): Boolean {
        f()
        println("Has been clicked")
        return true

    }

    /**
     * Listener which will listen the action on the drawer menu.
     * Using lambda function to navigate different screen
     * Using when statement(Kotlin's unique statement, similar to switch statement)
     *
     */
    fun setListener(){
        mNavigationView.setNavigationItemSelectedListener{
            item ->
            when(item.itemId){
                R.id.nav_Home -> goto { mContext.startActivity(Intent(mActivity,HomeScreen::class.java)) }
                R.id.nav_Leader_board -> goto {  }
                R.id.nav_Meal -> goto { mContext.startActivity(Intent(mActivity, MyMeals::class.java)) }
                R.id.nav_Profile -> goto { mContext.startActivity(Intent(mActivity, ProfileScreen::class.java)) }
                R.id.nav_Walkout -> goto {  }
                R.id.nav_settings -> goto {  }
                R.id.nav_Friends -> goto {mContext.startActivity(Intent(mActivity,FriendScreen::class.java))}

            }
            true
        }
    }

    /**
     *
     */
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val mMenuInflater = menuInflater
        mMenuInflater.inflate(R.menu.nav_menu,menu)
        return true
    }
}