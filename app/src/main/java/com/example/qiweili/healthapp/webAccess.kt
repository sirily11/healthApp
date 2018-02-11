package com.example.qiweili.healthapp
import android.app.VoiceInteractor
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.android.volley.VolleyError
import org.json.JSONObject
import android.widget.Toast
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonArrayRequest
import com.google.gson.JsonArray
import org.json.JSONArray


/**
 * getting the json file from internet
 */
class webAccess {

    constructor(address:String = "", port : Int = 80){
        println("")
    }

    fun getJson(que : RequestQueue,url : String):JSONArray {
        val url = "https://api.opendota.com/api/players/178510306/recentMatches"
        var rep = JSONArray()
        val jsArr = JsonArrayRequest(Request.Method.GET, url, null,
                Response.Listener {

                response ->
                    rep = response
                    println(rep)

        },
                Response.ErrorListener {
                    println("Error")
        })
        que.add(jsArr)
        return rep

    }


}