package com.example.qiweili.healthapp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import java.util.Random

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        randomButton.setOnClickListener {
            HelloworldText.text = randomChoice()
        }
    }

    fun randomChoice():String{
        val Array = arrayOf("1","2","3")
        val random = Random()
        val num = random.nextInt(Array.size)

        return Array[num]

    }
}
