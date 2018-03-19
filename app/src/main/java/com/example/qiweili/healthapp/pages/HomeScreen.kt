package com.example.qiweili.healthapp.pages

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.amitshekhar.DebugDB
import com.example.qiweili.healthapp.DatabaseHelper
import com.example.qiweili.healthapp.Drawer_menu
import com.example.qiweili.healthapp.R
import com.example.qiweili.healthapp.health.HealthData
import com.example.qiweili.healthapp.health.MyAdapter
import com.example.qiweili.healthapp.health.home_details
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.fitness.Fitness
import com.google.android.gms.fitness.FitnessOptions
import com.google.android.gms.fitness.data.DataType
import com.google.android.gms.fitness.data.Field
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_homescreen.*
import kotlinx.android.synthetic.main.collapsing_toolbar.*
import kotlinx.android.synthetic.main.row_main_home.view.*
import kotlin.math.roundToInt


class HomeScreen : AppCompatActivity() {
    //To create an drawerview toggle with optional type
    var myDrawerToggle: ActionBarDrawerToggle? = null
    val code = 1
    /**
     * Health data map. <Health Data Type, Value>
     */
    var healthDataMap = mutableMapOf<String, Int>()
    var healthDataList = mutableListOf<HealthData>()
    var db : DatabaseHelper? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_homescreen)
        setSupportActionBar(toolbar)

        val drawer_layout = Drawer_menu(this,
                this@HomeScreen, drawer_layout_home, nav_Home)

        drawer_layout.setListener()

        val fitnessOptions =
                FitnessOptions.builder()
                        .addDataType(DataType.TYPE_STEP_COUNT_DELTA)
                        .addDataType(DataType.TYPE_STEP_COUNT_CUMULATIVE)
                        .build()

        myDrawerToggle = drawer_layout.mDrawerToggle
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setTitle("Home")
        /**
         * Create a database
         */
        db = DatabaseHelper(this, "Health.db")

        DataView.layoutManager = LinearLayoutManager(DataView.context)
        if (!GoogleSignIn.hasPermissions(GoogleSignIn.getLastSignedInAccount(this), fitnessOptions)) {
            GoogleSignIn.requestPermissions(
                    this,
                    code,
                    GoogleSignIn.getLastSignedInAccount(this),
                    fitnessOptions)
        } else {
            DebugDB.getAddressLog()
            subscribe()
            readDaily()
            try {
                readFromDatabase("Steps", 1)
            }catch (e : IndexOutOfBoundsException){

            }
            DataView.adapter = MyAdapter(this, data = healthDataList)

        }
    }


    fun subscribe() {
        Fitness.getRecordingClient(this, GoogleSignIn.getLastSignedInAccount(this))
                .subscribe(DataType.TYPE_ACTIVITY_SAMPLES)
                .addOnSuccessListener { Log.i("Homescreen", "Successfully subscribed!") }
                .addOnFailureListener { Log.i("Homescreen", "There was a problem subscribing.") }
    }


    fun readDaily() {

        Fitness.getHistoryClient(this, GoogleSignIn.getLastSignedInAccount(this))
                .readDailyTotalFromLocalDevice(DataType.TYPE_STEP_COUNT_DELTA)
                .addOnSuccessListener { dataset ->
                    var total = 0
                    if (!dataset.isEmpty) {
                        total = dataset.dataPoints[0].getValue(Field.FIELD_STEPS).asInt()
                        healthDataMap.put("Steps", total)
                        writeToDatabase(total, "Steps")
                        readFromDatabase("Steps", 1)
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
                        //writeToDatabase(total, "Calories")
                    } else {

                    }
                }
    }

    fun readFromDatabase(description: String,index: Int) {
        val account_id = FirebaseAuth.getInstance().currentUser?.uid
        val steps = db?.getSteps(account_id!!)
        healthDataList.add(HealthData(steps,"Steps"))
    }

    fun writeToDatabase(data: Int, description: String) {
        val account_id = FirebaseAuth.getInstance().currentUser?.uid
        val success = db?.insertData(account_id,"Qiwei",data,null,null,null,null,null)
        if(success == true){
            //Toast.makeText(this,"Success " + data,Toast.LENGTH_SHORT).show()
        }else{
            Toast.makeText(this,"False",Toast.LENGTH_SHORT).show()
        }

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
        init {
           view.card_view.setOnClickListener {
               val intent = Intent(view.context, home_details::class.java)
               view.context.startActivity(intent)
           }
        }
    }
}