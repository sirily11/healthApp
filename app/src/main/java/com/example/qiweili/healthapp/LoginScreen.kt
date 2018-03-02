package com.example.qiweili.healthapp

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_loginscreen.*
import android.widget.Toast
import com.example.qiweili.healthapp.R.color.common_google_signin_btn_text_light
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.FirebaseApp
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.AuthResult
import com.google.android.gms.tasks.Task
import android.support.annotation.NonNull
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.AuthCredential




/**
 * This is the login page which required users to input their password
 * and email.
 */

class LoginScreen : AppCompatActivity() {
    val mAuth = FirebaseAuth.getInstance()
    var gso : GoogleSignInOptions? = null
    var mGoogleSignInClient : GoogleSignInClient? = null
    val RC_SIGN = 2


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loginscreen)
        FirebaseApp.initializeApp(this)
        gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()

        mGoogleSignInClient = GoogleSignIn.getClient(this,gso!!)

        signInBtn.setOnClickListener(View.OnClickListener {
            View -> login()
        })
        SignUp.setOnClickListener {
            val intent = Intent(this, SignUpScreen::class.java)
            startActivity(intent)
        }

        googleSigninBtn.setOnClickListener {
                View -> signIn()
        }
    }

    private fun signIn() {
        val signInIntent = mGoogleSignInClient?.signInIntent
        startActivityForResult(signInIntent, RC_SIGN)
    }

    override fun onActivityResult(requestCode : Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode,resultCode, data)
        if(requestCode == RC_SIGN){
            val result = Auth.GoogleSignInApi.getSignInResultFromIntent(data)
            if (result.isSuccess){
                val account = result.signInAccount
                firebaseAuthWithGoogle(account)
            }else{
                Log.e("LoginScreen","Login to Google failed")
                loginWithGoogle(null)
            }
        }
    }

    private fun firebaseAuthWithGoogle(account: GoogleSignInAccount?) {

        Log.d("Loginscreen","firebaseAuthWithGoogle ${account?.id}")

        val credential = GoogleAuthProvider.getCredential(account?.getIdToken(), null)
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d("Loginscreen", "signInWithCredential:success")
                        val user = mAuth.currentUser
                        loginWithGoogle(user)
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w("Loginscreen", "signInWithCredential:failure", task.exception)
                        loginWithGoogle(null)
                    }
                }
    }
    private fun loginWithGoogle(user : FirebaseUser?){
       if(user != null){
           val intent = Intent(this, HomeScreen::class.java)
           startActivity(intent)

       }else{

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
