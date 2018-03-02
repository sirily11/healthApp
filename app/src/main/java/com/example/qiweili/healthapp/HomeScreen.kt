package com.example.qiweili.healthapp

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.MenuItem
import android.view.View
import com.example.qiweili.healthapp.health.HealthData
import com.example.qiweili.healthapp.health.MyAdapter
import com.example.qiweili.utils
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.fitness.Fitness
import com.google.android.gms.fitness.FitnessOptions
import com.google.android.gms.fitness.data.DataType
import com.google.android.gms.fitness.data.Field
import com.google.android.gms.fitness.request.OnDataPointListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_homescreen.*
import kotlinx.android.synthetic.main.collapsing_toolbar.*
import kotlin.math.roundToInt


class HomeScreen : AppCompatActivity() {
    //To create an drawerview toggle with optional type
    var myDrawerToggle: ActionBarDrawerToggle? = null
    var mClient: GoogleApiClient? = null
    var mListener: OnDataPointListener? = null
    val code = 1
    var healthDataMap = mutableMapOf<String, Int>()
    var healthDataList = mutableListOf<HealthData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_homescreen)
        setSupportActionBar(toolbar)

        val user = FirebaseAuth.getInstance().currentUser?.email
        DataView.layoutManager = LinearLayoutManager(DataView.context)

        //windows_text.text = "Welcome $user"

        supportActionBar?.setTitle("Home")
        val drawer_layout = Drawer_menu(this,
                this@HomeScreen, drawer_layout_home, nav_Home)
        myDrawerToggle = drawer_layout.mDrawerToggle
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        drawer_layout.setListener()

        val fitnessOptions =
                FitnessOptions.builder()
                        .addDataType(DataType.TYPE_STEP_COUNT_DELTA)
                        .addDataType(DataType.TYPE_STEP_COUNT_CUMULATIVE)
                        .build()

        if (!GoogleSignIn.hasPermissions(GoogleSignIn.getLastSignedInAccount(this), fitnessOptions)) {
            GoogleSignIn.requestPermissions(
                    this,
                    code,
                    GoogleSignIn.getLastSignedInAccount(this),
                    fitnessOptions)
        } else {
            subscribe()
            readDaily()
            healthDataList.add(HealthData(null,null))
            healthDataList.add(HealthData(null,null))
            readFromDatabase("Calories",0)
            readFromDatabase("Steps",1)
            DataView.adapter = MyAdapter(this, data = healthDataList)
        }
    }

    fun updateRead(description: String,index : Int) {
        val timerHandler = Handler()
        var updater: Runnable? = null
        updater = Runnable {
            try {
                updateData()
                //healthDataList.removeAt(index)


            } catch (e: Exception) {

            }
            healthDataList.set(index,HealthData(healthDataMap.get(description), description))

            DataView.adapter.notifyDataSetChanged()

            timerHandler.postDelayed(updater, 5000)
        }
        timerHandler.post(updater)
    }

    fun updateData() {
            readDaily()
    }

    fun subscribe() {
        Fitness.getRecordingClient(this, GoogleSignIn.getLastSignedInAccount(this))
                .subscribe(DataType.TYPE_ACTIVITY_SAMPLES)
                .addOnSuccessListener { Log.i("Homescreen", "Successfully subscribed!") }
                .addOnFailureListener { Log.i("Homescreen", "There was a problem subscribing.") }
    }


    fun readDaily() {

        var isSuccessFul = false

        Fitness.getHistoryClient(this, GoogleSignIn.getLastSignedInAccount(this))
                .readDailyTotalFromLocalDevice(DataType.TYPE_STEP_COUNT_DELTA)
                .addOnSuccessListener { dataset ->
                    var total = 0
                    if (!dataset.isEmpty) {
                        total = dataset.dataPoints[0].getValue(Field.FIELD_STEPS).asInt()
                        healthDataMap.put("Steps", total)
                        writeToDatabase(total, "Steps")
                    } else {

                    }
                }

        Fitness.getHistoryClient(this, GoogleSignIn.getLastSignedInAccount(this))
                .readDailyTotalFromLocalDevice(DataType.TYPE_CALORIES_EXPENDED)
                .addOnSuccessListener { dataset ->
                    var total: Int
                    if (!dataset.isEmpty) {
                        total = dataset.dataPoints[0].getValue(Field.FIELD_CALORIES).asFloat().roundToInt()
                        healthDataMap.put("Calories", total)
                        writeToDatabase(total, "Calories")
                    } else {

                    }
                }
    }

    fun readFromDatabase(description: String,index: Int) {
        val userUID = FirebaseAuth.getInstance().currentUser?.uid
        val database = utils.getDatabase()
        val myRef = database.getReference("HealthApp/Data/$userUID/HealthData/${description}")
        myRef.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError?) {
                Log.i("Error", "Error")
            }

            override fun onDataChange(dataSnapshot: DataSnapshot?) {
                val value = dataSnapshot?.getValue(Int::class.java)
                healthDataMap.put(description, value!!)
                updateRead(description,index)
            }
        })
    }

    fun writeToDatabase(data: Int, description: String) {
        val userUID = FirebaseAuth.getInstance().currentUser?.uid
        val database = utils.getDatabase()
        val myRef = database.getReference("HealthApp/Data/$userUID/HealthData/${description}")
        myRef.setValue(data)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        println("Item is $item")
        if (myDrawerToggle?.onOptionsItemSelected(item)!!) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == code) {

            }
        }
    }


    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

    }
}
