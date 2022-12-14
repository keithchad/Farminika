package com.farminikagroup.farminika.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.farminikagroup.farminika.R
import com.farminikagroup.farminika.data.utils.Extensions.toast
import com.farminikagroup.farminika.data.auth.SignUpScreen
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_welcome_screen.*
import kotlinx.android.synthetic.main.bottom_sheet_layout.*

class DecisionScreen : AppCompatActivity() {
    private lateinit var firebaseAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome_screen)

        signOutUser()
        initializeBottomSheet()
    }

    //Sign Out User
    private fun signOutUser() {

        firebaseAuth = Firebase.auth

        signOutButton.setOnClickListener {
            firebaseAuth.signOut()
            toast("User has been signed out")

            val intent = Intent(this, SignUpScreen::class.java)
            startActivity(intent)
            finish()

        }
    }

    //Initialize BottomSheet
    private fun initializeBottomSheet() {
        infoButton.setOnClickListener {
            val bottomSheet = BottomSheetDialog(this)
            val view = layoutInflater.inflate(R.layout.bottom_sheet_layout, null)
            bottomSheetLayout.setOnClickListener {
                bottomSheet.dismiss()
            }
            bottomSheet.setCancelable(true)
            bottomSheet.setContentView(view)
            bottomSheet.show()
        }
    }
}