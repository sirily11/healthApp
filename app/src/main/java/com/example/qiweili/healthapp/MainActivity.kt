package com.example.qiweili.healthapp

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

/**
 * This is the page where user decide to login or not
 */

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        LoginBtn.setOnClickListener {
            val intent = Intent(this, DotaActivity::class.java)
            startActivity(intent)
            //val web = webAccess()
            //val que = Volley.newRequestQueue(this@MainActivity)
            //var msg = web.getJson(que)


        }
    }


}
