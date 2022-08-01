package com.farminikagroup.farminika.data.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.farminikagroup.farminika.ui.activity.DecisionScreen
import com.farminikagroup.farminika.data.utils.Extensions.toast
import com.farminikagroup.farminika.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_sign_in_screen.*

class SignInScreen : AppCompatActivity() {

    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in_screen)
        initializeFirebase()
    }

    private fun initializeFirebase() {
        textSignUp.setOnClickListener {
            val intent = Intent(this, SignUpScreen::class.java)
            startActivity(intent)
        }
        firebaseAuth = Firebase.auth

        signinButton.setOnClickListener {
            toast("Signing In has started!")
            performSignIn()
        }

    }

    private fun performSignIn() {
        val email = editTextEmailSignIn.text.toString()
        val password = editTextPasswordSignIn.text.toString()

        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) {
                toast("Signing In has ended!")
                if(it.isSuccessful) {
                    val intent = Intent(this, DecisionScreen::class.java)
                    startActivity(intent)
                    finish()
                    toast("Signing In was successful")
                } else {
                    toast("Failed to Sign In")
                }
            }.addOnFailureListener(this) {
                toast(it.toString())
            }
    }
}