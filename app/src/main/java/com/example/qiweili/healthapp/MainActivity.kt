package com.example.qiweili.healthapp

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_firstscreen.*
import android.Manifest.permission.WRITE_CALENDAR
import android.content.pm.PackageManager
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.util.Log
import io.vrinda.kotlinpermissions.PermissionCallBack
import io.vrinda.kotlinpermissions.PermissionsActivity


/**
 * This is the page where user decide to login or not
 */

class MainActivity : PermissionsActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_firstscreen)
        LoginBtn.setOnClickListener {
            val intent = Intent(this, LoginScreen::class.java)
            startActivity(intent)
        }


        requestPermissions(arrayOf(android.Manifest.permission.BODY_SENSORS,
                                   android.Manifest.permission.ACCESS_FINE_LOCATION), object : PermissionCallBack {
            override fun permissionGranted() {
                super.permissionGranted()
                Log.v("Location permissions", "Granted")
            }

            override fun permissionDenied() {
                super.permissionDenied()
                Log.v("Location permissions", "Denied")
            }
        })

    }
}
