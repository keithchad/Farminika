package com.farminikagroup.farminika.data.auth

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.farminikagroup.farminika.R
import com.farminikagroup.farminika.data.model.User
import com.farminikagroup.farminika.data.utils.Constants
import com.farminikagroup.farminika.data.utils.Extensions.toast
import com.farminikagroup.farminika.ui.activity.MainActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_sign_up_screen.*

class SignUpScreen : AppCompatActivity() {

    //Prepare variables for late initialization
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var firebaseUser: FirebaseUser
    private lateinit var databaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up_screen)
        initializeFirebase()
    }

    //Initialize Firebase
    private fun initializeFirebase() {

        val profession = intent.getStringExtra(Constants.INTENT_EXTRA_PROFESSION)

        if (profession != null) {

            if (profession == Constants.INTENT_EXTRA_FARMER) {
                inputLayoutEducation.visibility = View.GONE
                inputLayoutExpertise.visibility = View.GONE
            }
        } else {
            toast("Profession is Null")
        }

        textSignIn.setOnClickListener {
            val intent = Intent(this, SignInScreen::class.java)
            startActivity(intent)
        }
        firebaseAuth = Firebase.auth

        signupButton.setOnClickListener {
            signUpProgressBar.visibility = View.VISIBLE
            signupButton.visibility = View.INVISIBLE
            performSignIn()
        }

    }

    //Sign Up using Email and Password
    private fun performSignIn() {
        val email = editTextEmail.text.toString()
        val password = editTextPassword.text.toString()

        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) {

                signUpProgressBar.visibility = View.INVISIBLE
                signupButton.visibility = View.VISIBLE

                if(it.isSuccessful) {

                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()

                    sendUserDataToFirebase()
                    sendEmailVerification(email)
                } else {
                    toast("Failed to Sign Up")
                }
            }.addOnFailureListener(this) {
                toast(it.toString())
            }

    }

    //Send User Data to Firebase Database
    private fun sendUserDataToFirebase() {
        firebaseUser = firebaseAuth.currentUser!!
        val userId = firebaseUser.uid
        val userName = "user01"
        val name = editTextName.text.toString()
        val email = editTextEmail.text.toString()
        val location = "Unknown"
        val phoneNumber = "Empty"
        val profileImage = ""

        databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(userId)
        val user = User(userId, profileImage, name, userName, email, location, phoneNumber)

        databaseReference.setValue(user).addOnSuccessListener {
            editTextName.text.clear()
            editTextEmail.text.clear()
            toast("Data has been successfully saved")
        }. addOnFailureListener{
            toast("Failed to saved Data")
        }
    }

    //Send Email Verification to User Email
    private fun sendEmailVerification(email: String) {

        firebaseUser = firebaseAuth.currentUser!!
        firebaseUser.sendEmailVerification().addOnCompleteListener(this) {
            if (it.isSuccessful) {
                toast("Email has been sent to $email")
            }
        }
    }

}