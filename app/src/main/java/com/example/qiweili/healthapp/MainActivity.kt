package com.example.qiweili.healthapp

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import jp.wasabeef.blurry.Blurry
import kotlinx.android.synthetic.main.activity_firstscreen.*



/**
 * This is the page where user decide to login or not
 */

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_firstscreen)
        LoginBtn.setOnClickListener {
            val intent = Intent(this, HomeScreen::class.java)
            startActivity(intent)
        }
    }


}
