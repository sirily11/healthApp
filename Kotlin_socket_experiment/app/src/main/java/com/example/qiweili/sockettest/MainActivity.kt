package com.example.qiweili.sockettest

import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import client
import android.R.attr.duration
import android.R.attr.progress
import android.annotation.SuppressLint
import android.widget.SeekBar


class MainActivity : AppCompatActivity() {
    var message = ""
    var address = "192.168.86.84"

    @SuppressLint("ShowToast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        upBtn.setOnClickListener {
            message = "up"
            val send = sendInstructions()
            send.execute()
        }

        downBtn.setOnClickListener {
            message = "down"
            val send = sendInstructions()
            send.execute()
        }

        leftBtn.setOnClickListener {
            message = "left"
            val send = sendInstructions()
            send.execute()
        }

        rightBtn.setOnClickListener {
            message = "right"
            val send = sendInstructions()
            send.execute()
        }

        setIPAddressBtn.setOnClickListener {
            val text = ipAddressText.text
            address = text.toString()
            var message = "Already set the IP address to " + address
            val duration = Toast.LENGTH_SHORT
            val toast = Toast.makeText(applicationContext,message,duration).show()
            //println(text)
        }
        //Speed set
        speedSlider.max = 500
        speedSlider.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                speedText.text = "Speed:  " +  p1.toString()
                /**
                val toast = Toast.makeText(applicationContext,
                        progress.toString(),duration).show()**/

            }

            override fun onStartTrackingTouch(p0: SeekBar) {
               println("Start")
            }

            override fun onStopTrackingTouch(p0: SeekBar) {
                val duration = Toast.LENGTH_SHORT
                message = p0.progress.toString()
                val send = sendInstructions()
                send.execute()
                val toast = Toast.makeText(applicationContext,
                        "Set speed at: "+ message,duration).show()
            }
        })



    }

    private inner class sendInstructions : AsyncTask<String,String,String>() {
        override fun doInBackground(vararg p0: String?): String? {
            val socketClinet = client(address, 5555)
            socketClinet.sendMsg(message)
            socketClinet.closeSocket()
            return ""
        }

    }
}
