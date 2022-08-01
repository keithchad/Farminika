package com.farminikagroup.farminika.data.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.AnimationUtils
import com.farminikagroup.farminika.R
import kotlinx.android.synthetic.main.activity_starting_screen.*

class StartingScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_starting_screen)
        initializeLayout()
    }

    private fun initializeLayout() {
        val animationSlideUp = AnimationUtils.loadAnimation(this, R.anim.slide_up)
        linearLayoutStarting.startAnimation(animationSlideUp)

        buttonExpert.setOnClickListener {
            val intent = Intent(this, SignUpScreen::class.java)
            intent.putExtra("profession", "Expert")
            startActivity(intent)
        }
        buttonFarmer.setOnClickListener {
            val intent = Intent(this, SignUpScreen::class.java)
            intent.putExtra("profession", "Farmer")
            startActivity(intent)
        }
    }
}