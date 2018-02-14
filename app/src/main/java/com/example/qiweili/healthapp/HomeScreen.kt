package com.example.qiweili.healthapp

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_homescreen.*
import okhttp3.*
import java.io.IOException

class HomeScreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_homescreen)
        DotaView.layoutManager = LinearLayoutManager(DotaView.context)
        getData(this)

    }
    fun getData(context: Context){
        //Internet stuff
        val url = "http://52.207.147.141/data/?account="
        val userId = "178510306"
        //val mode = "/recentMatches"
        //end

        val client = OkHttpClient()
        val request = Request.Builder().url(url+userId).build()
        client.newCall(request).enqueue(object : Callback{
            override fun onFailure(call: Call?, e: IOException?) {
                println("Error")
            }

            override fun onResponse(call: Call?, response: Response?) {
                val body = response?.body()?.string()
                val gson = GsonBuilder().create()
                val data : List<DataAPI> = gson.fromJson(body,Array<DataAPI>::class.java).toList()
                runOnUiThread {
                    DotaView.adapter = MyAdapter(data = data, context = context)
                }
            }
        })
    }

    class ViewHolder(val view : View) : RecyclerView.ViewHolder(view){

    }
}
