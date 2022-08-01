package com.farminikagroup.farminika.ui.activity

import android.app.FragmentTransaction
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.fragment.app.FragmentManager
import com.farminikagroup.farminika.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        bottomNavigation.setOnItemSelectedListener(object: NavigationBarView.OnItemSelectedListener{
//            override fun onNavigationItemSelected(item: MenuItem): Boolean {
//                when (item.itemId) {
//                    R.id.home ->
//                    R.id.blog ->
//                    R.id.chat ->
//                    R.id.profile ->
//                }
//               return true
//            }
//
//        })
    }

//    fun replaceFragment() {
//        val fragmentManager: FragmentManager = supportFragmentManager
//        val fragmentTransaction: androidx.fragment.app.FragmentTransaction = fragmentManager.beginTransaction()
//        fragment
//    }
}