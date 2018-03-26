package com.example.qiweili.healthapp.pages

import android.app.Activity
import android.os.Bundle
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.example.qiweili.healthapp.DatabaseHelper
import com.example.qiweili.healthapp.Drawer_menu
import com.example.qiweili.healthapp.R
import com.example.qiweili.utils
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_profile_page.*
import kotlinx.android.synthetic.main.alert_dialog_window_meal.view.*
import kotlinx.android.synthetic.main.collapsing_toolbar.*
import kotlinx.android.synthetic.main.content_profile_page.*
import android.provider.MediaStore
import android.content.Intent
import android.graphics.Bitmap
import android.provider.ContactsContract
import java.io.ByteArrayOutputStream


class ProfileScreen : AppCompatActivity() {
    var myDrawerToggle: ActionBarDrawerToggle? = null
    val db = DatabaseHelper(this,utils.databaseName)
    val REQUEST_IMAGE_CAPTURE = 1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_page)
        setSupportActionBar(toolbar)
        val account_id = FirebaseAuth.getInstance().currentUser?.uid
        val drawer_layout = Drawer_menu(this, this@ProfileScreen, drawer_layout_profile, nav_Profile)

        supportActionBar?.setTitle("Profile")
        myDrawerToggle = drawer_layout.mDrawerToggle
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        drawer_layout.setListener()
        if(db.getProfileImage(utils.account_id!!) != null){
            imageView2.setImageBitmap(db.getProfileImage(utils.account_id))
        }
        try {
            about_me.setText(db.getAboutme(account_id!!))
        }catch (e : IllegalStateException){
            about_me.setText("About me")
        }
        about_me.setOnFocusChangeListener { view, hasFocus ->
            if (!hasFocus) {
                hideSoftKeyboard(view = view)
            }
        }

        fab.setOnClickListener {
            view ->
            val aboutMe: String = about_me.text.toString()
            val profileName = profile_name.text.toString()
            db.updateAboutme(account_id = account_id!!,about_me = aboutMe)
            db.updateProfileName(profileName,utils.account_id)
            db.push_to_server(DatabaseHelper.PROFILE_NAME)
            db.push_to_server(DatabaseHelper.ABOUTME_DESCRIBE)
            recreate()
            hideSoftKeyboard(view)
        }

        camera.setOnClickListener {
            openCamera()
        }
    }


    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        println("Item is $item")
        if (myDrawerToggle?.onOptionsItemSelected(item)!!) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    fun hideSoftKeyboard(view: View) {
        val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0)
    }

    fun openCamera(){
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (takePictureIntent.resolveActivity(packageManager) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK){
            val extras = data?.extras
            val image = extras?.get("data") as Bitmap
            val stream = ByteArrayOutputStream()
            image.compress(Bitmap.CompressFormat.PNG, 100, stream)
            val byteArray = stream.toByteArray()
            imageView2.setImageBitmap(image)
            db.updateProfileImage(byteArray,utils.account_id!!)
            db.push_to_server(DatabaseHelper.PROFILE_PIC)

        }
    }
}
