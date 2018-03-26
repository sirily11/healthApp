package com.example.qiweili.healthapp.friend

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.PopupMenu
import android.support.v7.widget.RecyclerView
import android.view.*
import com.example.qiweili.healthapp.DatabaseHelper
import com.example.qiweili.healthapp.Drawer_menu
import com.example.qiweili.healthapp.R
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_friend_screen.*
import kotlinx.android.synthetic.main.alert_dialog_window_friend.view.*
import kotlinx.android.synthetic.main.collapsing_toolbar.*
import kotlinx.android.synthetic.main.row_main_friend.view.*
import okhttp3.*
import java.io.IOException

class FriendScreen : AppCompatActivity() {
    var myDrawerToggle: ActionBarDrawerToggle? = null
    var friends = mutableListOf<Friend>()
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_friend_screen)
        setSupportActionBar(toolbar)
        friendlist.layoutManager = LinearLayoutManager(friendlist.context)
        windows_text.text = ""
        supportActionBar?.setTitle("Friends")
        friendlist.adapter = MyAdapterForFriendScreen(this,friends)

        val drawer_layout = Drawer_menu(this, this@FriendScreen, drawer_layout_friend, nav_friend)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        myDrawerToggle = drawer_layout.mDrawerToggle
        drawer_layout.setListener()

    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val mMenuInflater = menuInflater
        mMenuInflater.inflate(R.menu.menu_add_friends, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        println("Item is $item")
        if (myDrawerToggle?.onOptionsItemSelected(item)!!) {
            return true
        }
        when(item?.itemId){
            R.id.add_friend ->{
                showAddFriend()
            }

        }
        return super.onOptionsItemSelected(item)
    }

    /**
     * This is a helper function which will show the
     * pop up window about the add friend
     */
    fun showAddFriend(){
        /**
         * Build the pop up window
         */
        val mBuilder = AlertDialog.Builder(this)
        val inflater = LayoutInflater.from(this)
        val view = inflater.inflate(R.layout.alert_dialog_window_friend, null)
        mBuilder.setView(view)
        val dialog = mBuilder.create()

        /**
         * Add friend
         */
        view.add_button.setOnClickListener {
            val profileName = view.userName.text.toString()
            if(!profileName.isEmpty()){
                val gson = GsonBuilder().create()
                val client = OkHttpClient()
                val request = Request.Builder().url("http://192.168.86.105:8080/get/add_friends/?${DatabaseHelper.PROFILE_NAME}=$profileName").build()
                dialog.dismiss()
                client.newCall(request).enqueue(object : Callback {
                    override fun onFailure(call: Call?, e: IOException?) {
                        //view.warning_msg.text = "Failed"
                    }

                    override fun onResponse(call: Call?, response: Response?) {
                        val body = response?.body()?.string()
                        val gson = GsonBuilder().create()
                        val data = gson.fromJson(body, Array<Friend>::class.java).toList()
                        runOnUiThread {
                            friends.add(data[0])
                            friendlist.adapter.notifyDataSetChanged()
                        }
                        dialog.dismiss()
                    }
                })
            }
        }
        view.cancel_button.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }

    /**
     *helper method which will fetch the data from internet and
     * phase the json data into the list of DataAPI's object
     *
     */
    fun getData(context: Context) {

        //Create an okHttpClient object
        val client = OkHttpClient()
        //initialize the request
        val request = Request.Builder().url("").build()

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
                val data = gson.fromJson(body, Array<Friend>::class.java).toMutableList()
                runOnUiThread {
                    friendlist.adapter = MyAdapterForFriendScreen(data = data, context = context)
                }
            }
        })
    }
    class ViewHolder(val view: View, val friends : MutableList<Friend>, val adapterForFriendScreen: MyAdapterForFriendScreen) : RecyclerView.ViewHolder(view) {
        init {
            view?.edit_friend_button?.setOnClickListener {
                showMenu(view)
            }
        }

        fun showMenu(v: View) {
            val popup = PopupMenu(view?.context!!, view, Gravity.NO_GRAVITY)
            popup.inflate(R.menu.menu_edit)
            popup.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.delete -> {
                        friends.removeAt(adapterPosition)
                        adapterForFriendScreen?.notifyDataSetChanged()
                        //var db = DatabaseHelper(view.context, utils.databaseName)
                        //db.updateFood(meals!!, utils.account_id!!)
                        true
                    }
                    else -> {
                        true
                    }
                }
            }

            popup.show()
        }
    }
}
