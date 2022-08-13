package com.farminikagroup.farminika.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.farminikagroup.farminika.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        //signOutUser()
        val lookingButton = view.findViewById<TextView>(R.id.lookingButton)
        lookingButton.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_sectorBottomSheet)
        }
        return view
    }

    private fun signOutUser() {

        firebaseAuth = Firebase.auth

        signOutButton.setOnClickListener {
            firebaseAuth.signOut()

            findNavController().navigate(R.id.action_homeFragment_to_introScreenActivity)

        }
    }


}