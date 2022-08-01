package com.farminikagroup.farminika.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.farminikagroup.farminika.R
import com.farminikagroup.farminika.data.auth.StartingScreen
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_splash_screen.*

class IntroScreenActivity : AppCompatActivity() {

    private lateinit var analytics: FirebaseAnalytics
    private lateinit var firebaseAuth: FirebaseAuth
    private var firebaseUser: FirebaseUser? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        initializeUser()
        initializeSplash()
        initializeFirebase()
        
    }

    private fun initializeUser() {
        firebaseAuth = Firebase.auth
        firebaseUser = firebaseAuth.currentUser
    }

    private fun initializeSplash() {

        splashImage.alpha = 0f
        splashText.alpha = 0f
        splashImage.animate().setDuration(2000).alpha(1f).withEndAction {
            if (firebaseUser ==  null) {
                val intent = Intent(this, StartingScreen::class.java)
                startActivity(intent)
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
                finish()
            }else {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
                finish()
            }
        }
        splashText.animate().setDuration(2000).alpha(1f)
    }

    private fun initializeFirebase() {
        analytics = Firebase.analytics
    }

}