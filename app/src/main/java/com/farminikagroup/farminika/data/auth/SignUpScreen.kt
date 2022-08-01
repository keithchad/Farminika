package com.farminikagroup.farminika.data.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.farminikagroup.farminika.ui.activity.DecisionScreen
import com.farminikagroup.farminika.data.utils.Extensions.toast
import com.farminikagroup.farminika.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_sign_up_screen.*

class SignUpScreen : AppCompatActivity() {

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var firebaseUser: FirebaseUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up_screen)
        initializeFirebase()
    }

    private fun initializeFirebase() {

        val intent = Intent()
        val profession = intent.getStringExtra("profession")

        if (profession == "Farmer") {
            inputLayoutEducation.visibility = View.GONE
            inputLayoutExpertise.visibility = View.GONE
        }

        textSignIn.setOnClickListener {
            val intent = Intent(this, SignInScreen::class.java)
            startActivity(intent)
        }
        firebaseAuth = Firebase.auth


        signupButton.setOnClickListener {
            //lottie.visibility = View.VISIBLE
            signupButton.visibility = View.INVISIBLE
            performSignIn()
        }

    }

    private fun performSignIn() {
        val email = editTextEmail.text.toString()
        val password = editTextPassword.text.toString()

        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) {
                //lottie.visibility = View.INVISIBLE
                signupButton.visibility = View.VISIBLE
                if(it.isSuccessful) {
                    val intent = Intent(this, DecisionScreen::class.java)
                    startActivity(intent)
                    finish()
                    sendEmailVerification(email)
                    toast("Signing In was successful")
                } else {
                    toast("Failed to Sign Up")
                }
            }.addOnFailureListener(this) {
                toast(it.toString())
            }

    }

    private fun sendEmailVerification(email: String) {
        firebaseUser = firebaseAuth.currentUser!!
        firebaseUser.sendEmailVerification().addOnCompleteListener(this) {
            if (it.isSuccessful) {
                toast("Email has been sent to $email")
            }
        }
    }


}