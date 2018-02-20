package com.example.qiweili.healthapp

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_loginscreen.*
import android.widget.Toast
import com.google.firebase.FirebaseApp


/**
 * This is the login page which required users to input their password
 * and email.
 */

class LoginScreen : AppCompatActivity() {
    val mAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loginscreen)
        FirebaseApp.initializeApp(this)
        signInBtn.setOnClickListener(View.OnClickListener {
            View -> login()
        })
        SignUp.setOnClickListener {
            val intent = Intent(this, SignUpScreen::class.java)
            startActivity(intent)
        }
    }
    private fun login(){
        val email = userName.text.toString()
        val password = password.text.toString()

        if(!email.isEmpty() && !password.isEmpty()){
            mAuth.signInWithEmailAndPassword(email,password)
                    .addOnCompleteListener(this,
                            OnCompleteListener { task ->
                                if(task.isSuccessful){
                                    val intent = Intent(this, HomeScreen::class.java)
                                    /**
                                     * If login is successful, then pass the user's ID to another screen
                                     */
                                    val userID = mAuth?.currentUser?.email
                                    intent.putExtra("UserID",userID)
                                    startActivity(intent)
                                    val context = applicationContext
                                    val text = "Successful"
                                    val duration = Toast.LENGTH_SHORT
                                    val toast = Toast.makeText(context, text, duration)
                                    toast.show()
                                    startActivity(intent)
                                }else{
                                    val context = applicationContext
                                    val text = "Wrong email or password combination!"
                                    val duration = Toast.LENGTH_SHORT
                                    val toast = Toast.makeText(context, text, duration)
                                    toast.show()
                                }
                            })
        }else{

        }
    }

}
