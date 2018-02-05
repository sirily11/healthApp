package com.example.qiweili.healthapp

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main2.*


class Main2Activity : AppCompatActivity() {
    val mAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        signInBtn.setOnClickListener(View.OnClickListener {
            View -> login()
        })
    }

    private fun login(){
        val email = userName.text.toString()
        val password = password.text.toString()

        if(!email.isEmpty() && !password.isEmpty()){
            mAuth.signInWithEmailAndPassword(email,password)
                    .addOnCompleteListener(this,
                            OnCompleteListener { task ->
                                if(task.isSuccessful){
                                    val intent = Intent(this,Main3Activity::class.java)
                                    startActivity(intent)
                                }else{

                                }
                            })
        }else{

        }
    }

}
