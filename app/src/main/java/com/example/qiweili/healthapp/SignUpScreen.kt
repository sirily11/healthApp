package com.example.qiweili.healthapp

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_sign_up_screen.*


class SignUpScreen : AppCompatActivity() {
    var mAuth: FirebaseAuth? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up_screen)
        mAuth = FirebaseAuth.getInstance()
        signin_password.setOnFocusChangeListener { view, hasFocus ->
            if (!hasFocus) {
                hideSoftKeyboard(view = view)
            }
        }
        signin_username.setOnFocusChangeListener { view, hasFocus ->
            if (!hasFocus) {
                hideSoftKeyboard(view = view)
            }
        }
        signin_signup.setOnClickListener {

            View ->
            signUp()
            println("Clicked on sign up")

        }

        /**
         *
        FirebaseApp.initializeApp(this)
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
        }**/
    }

    fun hideSoftKeyboard(view: View) {
        val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0)
    }

    private fun signUp() {
        var password = signin_password.text.toString()
        var email = signin_username.text.toString()
        if (!password.isEmpty() && !email.isEmpty()) {
            mAuth?.createUserWithEmailAndPassword(email, password)
                    ?.addOnCompleteListener(this,
                            OnCompleteListener
                            { task ->
                                if (task.isSuccessful) {
                                    println("Successful")
                                    Toast.makeText(this, "Successful",
                                            Toast.LENGTH_SHORT).show()
                                    val intent = Intent(this, HomeScreen::class.java)
                                    /**
                                     * If login is successful, then pass the user's ID to another screen
                                     */
                                    val userID = mAuth?.currentUser?.email
                                    intent.putExtra("UserID",userID)
                                    startActivity(intent)
                                } else {
                                    Toast.makeText(this, "Authentication failed",
                                            Toast.LENGTH_SHORT).show()
                                }

                            })
        }


    }
}
