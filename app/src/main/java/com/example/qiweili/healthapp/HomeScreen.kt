package com.example.qiweili.healthapp

import android.hardware.Sensor
import android.nfc.Tag
import android.os.Bundle
import android.provider.ContactsContract
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.NavigationView
import android.support.design.widget.Snackbar
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.MenuItem
import android.view.View
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.fitness.Fitness
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_homescreen.*
import kotlinx.android.synthetic.main.collapsing_toolbar.*
import com.google.android.gms.fitness.*
import com.google.android.gms.fitness.data.DataType.TYPE_ACTIVITY_SAMPLES
import com.google.android.gms.fitness.FitnessOptions
import com.google.android.gms.fitness.data.DataType
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.fitness.request.OnDataPointListener
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks
import com.google.android.gms.fitness.request.SensorRequest
import java.util.concurrent.TimeUnit
import com.google.android.gms.common.Scopes
import com.google.android.gms.common.api.Api
import com.google.android.gms.common.api.Scope
import com.google.android.gms.fitness.data.DataSource
import com.google.android.gms.fitness.request.DataSourcesRequest


class HomeScreen : AppCompatActivity() {
    //To create an drawerview toggle with optional type
    var myDrawerToggle: ActionBarDrawerToggle? = null
    var mClient : GoogleApiClient? = null
    var mListener : OnDataPointListener? = null
    var gso : GoogleSignInOptions? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_homescreen)
        setSupportActionBar(toolbar)
        //------------------------------------------------------------------------------------------

        val user = FirebaseAuth.getInstance().currentUser?.email
        println("Welcome \n User name is $user")

        //Create a view layout management-----------------------------------------------------------

        DataView.layoutManager = LinearLayoutManager(DataView.context)
        windows_text.text = "Welcome $user"
        supportActionBar?.setTitle("Home")

        //-----------------------Drawer menu--------------------------------------------------------

        val drawer_layout = Drawer_menu(this,
                this@HomeScreen, drawer_layout_home, nav_Home)

        myDrawerToggle = drawer_layout.mDrawerToggle
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        drawer_layout.setListener()

        //======================Google-Sign-in======================================================





        //=======================Google-signin======================================================



        Fitness.getSensorsClient(this,GoogleSignIn.getLastSignedInAccount(this))
                .findDataSources(
                        DataSourcesRequest.Builder()
                                .setDataTypes(DataType.TYPE_LOCATION_SAMPLE)
                                .setDataSourceTypes(DataSource.TYPE_RAW)
                                .build())
                .addOnSuccessListener {
                    for(i in it){
                        Log.i("HomeScreen","Data source found: ${i}")
                        Log.i("HomeScreen","Data source type: ${i.dataType.name}")

                        if(i.dataType.equals(DataType.TYPE_LOCATION_SAMPLE) && mListener == null){
                            registerFitnessDataListener(dataSource = i, dataType = DataType.TYPE_LOCATION_SAMPLE)
                        }
                    }
                }
                .addOnFailureListener {
                    Log.e("HomeScreen","Failed to add the listener")
                }
    }
    fun registerFitnessDataListener(dataSource : DataSource, dataType: DataType){
        mListener = OnDataPointListener {
            for(f in it.dataType.fields){
                val value = it.getValue(f)
                Log.i("HomeScreen", "Detected DataPoint field: ${f.name}")
                Log.i("HomeScreen", "Detected DataPoint value: $value")
            }
        }

        Fitness.getSensorsClient(this,GoogleSignIn.getLastSignedInAccount(this))
                .add(
                        SensorRequest.Builder()
                                .setDataSource(dataSource)
                                .setDataType(dataType)
                                .setSamplingRate(1,TimeUnit.SECONDS)
                                .build(),mListener
                )
                .addOnCompleteListener {
                    task ->
                    if(task.isSuccessful){
                        Log.i("HomeScreen", "Listener registered!");
                    }else{
                        Log.i("HomeScreen", "Listener Failed!");
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


    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

    }
}
