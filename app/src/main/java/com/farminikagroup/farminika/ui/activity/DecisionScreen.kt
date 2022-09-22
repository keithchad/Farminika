package com.farminikagroup.farminika.ui.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.farminikagroup.farminika.R
import com.farminikagroup.farminika.data.utils.Extensions.toast
import com.farminikagroup.farminika.data.auth.SignUpScreen
import com.farminikagroup.farminika.data.model.Users
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_welcome_screen.*
import kotlinx.android.synthetic.main.bottom_sheet_layout.*

class DecisionScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome_screen)

        initializeBottomSheet()
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

    private fun getName() {
        val firebaseAuth = FirebaseAuth.getInstance()
        FirebaseDatabase.getInstance().reference.child("Users").child(firebaseAuth.toString())
            .addValueEventListener(object: ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val user = snapshot.getValue(Users::class.java)
                    if (user != null) {
                        userNameTextDecision.text = user.name
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(applicationContext, error.message, Toast.LENGTH_SHORT).show()
                }

            })
    }
}