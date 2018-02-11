package com.example.qiweili.healthapp

import android.content.Context
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_dota.*
import kotlinx.android.synthetic.main.row_main.view.*
import okhttp3.*
import java.io.IOException

class DotaActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dota)
        DotaView.layoutManager = LinearLayoutManager(this)
        getData(this)

    }
    fun getData(context: Context){
        //Internet stuff
        val url = "https://api.opendota.com/api/players/"
        val userId = "178510306"
        val mode = "/recentMatches"
        //end

        val client = OkHttpClient()
        val request = Request.Builder().url(url+userId+mode).build()
        client.newCall(request).enqueue(object : Callback{
            override fun onFailure(call: Call?, e: IOException?) {
                println("Error")
            }

            override fun onResponse(call: Call?, response: Response?) {
                val body = response?.body()?.string()

                val gson = GsonBuilder().create()
                val data : List<DotaAPI> = gson.fromJson(body,Array<DotaAPI>::class.java).toList()
                println(data.get(0).getKDA())
                println(data.get(1).getKDA())
                runOnUiThread {
                    DotaView.adapter = MyAdapter(dota = data, context = context)
                }
            }
        })
    }
    private class MyAdapter(context : Context, dota : List<DotaAPI>) : RecyclerView.Adapter<ViewHolder>(){
        private var dota = dota
        private val myContext = context

        override fun getItemCount(): Int {
            return 10
        }

        override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
            val layoutInflater = LayoutInflater.from(parent?.context)
            val cellForRow = layoutInflater.inflate(R.layout.row_main,parent,false)
            return ViewHolder(cellForRow)
        }

        override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
            holder?.view?.hero_name?.text = "$position"
            holder?.view?.kda_ratio?.text = dota.get(position).getKDA()

        }
    }

    private class ViewHolder(val view : View) : RecyclerView.ViewHolder(view){

    }

    class DotaAPI(kills:Int, deaths:Int, assists:Int){
        private val kills = kills
        private val deaths = deaths
        private val assists = assists

        fun getKDA() : String{
            return "$kills : $deaths : $assists"
        }
    }

}
