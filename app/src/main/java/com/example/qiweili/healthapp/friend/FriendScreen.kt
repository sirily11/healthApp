package com.example.qiweili.healthapp.friend

import android.content.Context
import android.os.Bundle
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.MenuItem
import android.view.View
import com.example.qiweili.healthapp.DataAPI
import com.example.qiweili.healthapp.Drawer_menu
import com.example.qiweili.healthapp.R
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_friend_screen.*
import kotlinx.android.synthetic.main.collapsing_toolbar.*
import okhttp3.*
import java.io.IOException

class FriendScreen : AppCompatActivity() {
    var myDrawerToggle: ActionBarDrawerToggle? = null

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_friend_screen)
        setSupportActionBar(toolbar)
        friendlist.layoutManager = LinearLayoutManager(friendlist.context)
        windows_text.text = ""
        supportActionBar?.setTitle("Friends")

        //-----------------------Drawer menu--------------------------------------------------------
        val drawer_layout = Drawer_menu(this, this@FriendScreen, drawer_layout_friend, nav_friend)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        myDrawerToggle = drawer_layout.mDrawerToggle
        drawer_layout.setListener()
        //-----------------------Drawer menu--------------------------------------------------------
        //called the function which will handle the data
        //comes from the internet
        getData(this)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        println("Item is $item")
        if (myDrawerToggle?.onOptionsItemSelected(item)!!) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    /**
     *helper method which will fetch the data from internet and
     * phase the json data into the list of DataAPI's object
     *
     */
    fun getData(context: Context) {
        //Internet stuff
        val url = "http://52.207.147.141/data/?account="
        val userId = "178510306"
        //end

        //Create an okHttpClient object
        val client = OkHttpClient()
        //initialize the request
        val request = Request.Builder().url(url + userId).build()

        /**
         * This will actually fetch the data
         */
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call?, e: IOException?) {
                println("Error")
            }

            override fun onResponse(call: Call?, response: Response?) {
                val body = response?.body()?.string()
                val gson = GsonBuilder().create()
                val data: List<DataAPI> = gson.fromJson(body, Array<DataAPI>::class.java).toList()
                runOnUiThread {
                    friendlist.adapter = MyAdapterForFriendScreen(data = data, context = context)
                }
            }
        })
    }
    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

    }
}
