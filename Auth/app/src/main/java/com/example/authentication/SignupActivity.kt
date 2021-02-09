package com.example.authentication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Log.d
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_signup.*
import kotlinx.android.synthetic.main.activity_signup.signupbutton

class SignupActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
        init()
    }
    private fun init(){
        auth = Firebase.auth
        signupbutton.setOnClickListener {
            signup()

        }


    }
    private fun signup(){
        emailcheck()
        val email = emailedittext.text.toString()
        val password = passwordedittext.text.toString()
        val repeatpassword = repeatpasswordedittext.text.toString()

        if(email.isNotEmpty() && password.isNotEmpty() && repeatpassword.isNotEmpty()){
            if(password == repeatpassword){
                progressbar.visibility = View.VISIBLE
                signupbutton.isClickable = false

                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        progressbar.visibility = View.GONE
                        signupbutton.isClickable = true

                        if (task.isSuccessful) {
                            // Sign in success, update UI with the signed-in user's information
                            d("Signup", "createUserWithEmail:success")
                            val user = auth.currentUser
                            Toast.makeText(this, "signUp is success", Toast.LENGTH_SHORT).show()

                        } else {
                            // If sign in fails, display a message to the user.
                            d("Signup", "createUserWithEmail:failure", task.exception)
                            Toast.makeText(this, task.exception.toString(), Toast.LENGTH_SHORT).show()
                            Toast.makeText(baseContext, "Authentication failed.",
                                Toast.LENGTH_SHORT).show()

                        }


                    }

            }else{
                Toast.makeText(this, "Passwords doesnt match", Toast.LENGTH_SHORT).show()
            }

        }else{
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
        }

    }
    private fun emailcheck(){
        if(android.util.Patterns.EMAIL_ADDRESS.matcher(emailedittext.text.toString()).matches()){

        }else{
            emailedittext.setError("Email format is not correct")
        }
    }



}