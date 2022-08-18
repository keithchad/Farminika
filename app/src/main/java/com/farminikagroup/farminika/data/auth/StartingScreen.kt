package com.farminikagroup.farminika.data.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.AnimationUtils
import androidx.lifecycle.ViewModelProvider
import com.farminikagroup.farminika.R
import com.farminikagroup.farminika.data.viewmodel.ExtraViewModel
import com.farminikagroup.farminika.data.utils.Constants
import kotlinx.android.synthetic.main.activity_starting_screen.*

class StartingScreen : AppCompatActivity() {

    private lateinit var extraViewModel: ExtraViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_starting_screen)
        initializeLayout()
    }

    private fun initializeLayout() {

        //instantiate Viewmodel
        extraViewModel = ViewModelProvider(this)[ExtraViewModel::class.java]

        //Add Animation to Layout
        val animationSlideUp = AnimationUtils.loadAnimation(this, R.anim.slide_up)
        linearLayoutStarting.startAnimation(animationSlideUp)

        buttonExpert.setOnClickListener {
            val intent = Intent(this, SignUpScreen::class.java)
            extraViewModel.profession.value = Constants.INTENT_EXTRA_EXPERT
            intent.putExtra(Constants.INTENT_EXTRA_PROFESSION, Constants.INTENT_EXTRA_EXPERT)
            startActivity(intent)
        }

        buttonFarmer.setOnClickListener {
            val intent = Intent(this, SignUpScreen::class.java)
            extraViewModel.profession.value = Constants.INTENT_EXTRA_EXPERT
            intent.putExtra(Constants.INTENT_EXTRA_PROFESSION, Constants.INTENT_EXTRA_FARMER)
            startActivity(intent)
        }
    }
}