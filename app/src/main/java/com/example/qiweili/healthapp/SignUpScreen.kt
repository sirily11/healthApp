package com.example.qiweili.healthapp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.firebase.FirebaseApp
import kotlinx.android.synthetic.main.activity_sign_up_screen.*
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DataSnapshot




class SignUpScreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up_screen)
        FirebaseApp.initializeApp(this);
        signin_signup.setOnClickListener{
            val database = FirebaseDatabase.getInstance()
            val myRef = database.getReference("User name")
            val username : String = signin_username.text.toString()
            myRef.setValue(username)

            myRef.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    // This method is called once with the initial value and again
                    // whenever data at this location is updated.
                    val value = dataSnapshot.getValue(String::class.java)
                    signin_showusername.text = value

                }

                override fun onCancelled(error: DatabaseError) {
                    // Failed to read value
                    Log.w("Failed to read value.", error.toException())
                }
            })
        }
    }
}
