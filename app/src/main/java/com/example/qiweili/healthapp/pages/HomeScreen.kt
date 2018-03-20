package com.example.qiweili.healthapp.pages

import android.app.Activity
import android.arch.persistence.room.Database
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.util.AsyncListUtil
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.MenuItem
import android.view.View
import com.amitshekhar.DebugDB
import com.example.qiweili.healthapp.DatabaseHelper
import com.example.qiweili.healthapp.Drawer_menu
import com.example.qiweili.healthapp.R
import com.example.qiweili.healthapp.health.HealthData
import com.example.qiweili.healthapp.health.MyAdapter
import com.example.qiweili.healthapp.health.home_details
import com.example.qiweili.utils
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.fitness.Fitness
import com.google.android.gms.fitness.FitnessOptions
import com.google.android.gms.fitness.data.DataType
import com.google.android.gms.fitness.data.Field
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
    var healthDataListMap = mutableMapOf<String, MutableList<HealthData>>()
    var displayDataList = mutableListOf<HealthData>()
    var db: DatabaseHelper? = null

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
        db = DatabaseHelper(this, utils.databaseName)
        db?.insertAccountID(utils.account_id!!)
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
                readFromDatabase(DatabaseHelper.STEPS, 0)
                readFromDatabase(DatabaseHelper.CAL_BURNED, 1)

            } catch (e : Exception) {

            }
            DataView.adapter = MyAdapter(this, data = displayDataList)

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
                        writeToDatabase(total, DatabaseHelper.STEPS, utils.steps_index)
                        //displayDataList.add(HealthData(utils.getCurrentDate(),total,DatabaseHelper.STEPS))
                    } else {

                    }
                }

        Fitness.getHistoryClient(this, GoogleSignIn.getLastSignedInAccount(this))
                .readDailyTotalFromLocalDevice(DataType.TYPE_CALORIES_EXPENDED)
                .addOnSuccessListener { dataset ->
                    var total: Int
                    if (!dataset.isEmpty) {
                        total = dataset.dataPoints[0].getValue(Field.FIELD_CALORIES).asFloat().roundToInt()
                        writeToDatabase(total, DatabaseHelper.CAL_BURNED, utils.cal_burned_index)
                        //displayDataList.add(HealthData(utils.getCurrentDate(),total,DatabaseHelper.CAL_BURNED))
                    } else {

                    }
                }
    }

    /**
     * Read the data from database
     * @param description what type of data you want to read
     * @param index the index of you want to store in
     * 0 is for Steps
     * 1 is for calories
     */

    fun readFromDatabase(description: String, index: Int): MutableList<HealthData>? {
        when (description) {
            DatabaseHelper.STEPS -> {
                val steps = db?.getSteps(utils.account_id!!)
                val lsteps = utils.getLastHealthData(steps)
                if (lsteps != null) {
                    if (displayDataList.size >0){
                        displayDataList.set(0,lsteps)
                    } else {
                        displayDataList.add(lsteps)
                    }
                }
                return steps
            }
            DatabaseHelper.CAL_BURNED -> {
                val cals = db?.getCalsBurned(utils.account_id!!)
                val lcals = utils.getLastHealthData(cals)
                if (lcals != null) {
                    if (displayDataList.size >1){
                        displayDataList.set(1,lcals)
                    } else {
                        displayDataList.add(lcals)
                    }
                }
                return cals
            }
            else ->{
                return null
            }
        }

    }
    /**
     * write the data from database
     * @param description what type of data you want to write
     * @param data the data you want to write
     */

    fun writeToDatabase(data: Int, description: String, index: Int) {
        val time = utils.getCurrentDate()
        val historyData = mutableListOf<HealthData>()
        if(readFromDatabase(description,index) != null) {
            historyData.union(readFromDatabase(description, index)!!.toList())
        }
        if (historyData.size > 0 && utils.isSameDay(utils.getLastHealthData(historyData)?.time!!, time)) {
            historyData.set(historyData.size - 1, HealthData(time, data, description))
        } else {
            historyData.add(HealthData(time, data, description))
        }
        when (description) {
            DatabaseHelper.STEPS -> {
                db?.updateSteps(historyData, utils.account_id!!)
            }
            DatabaseHelper.CAL_BURNED -> {
                db?.updateCal_Burned(historyData, utils.account_id!!)
            }

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
