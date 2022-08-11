package com.farminikagroup.farminika.ui.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.farminikagroup.farminika.R
import com.farminikagroup.farminika.data.SectorViewModel
import com.farminikagroup.farminika.data.auth.SignUpScreen
import com.farminikagroup.farminika.data.utils.Extensions.toast
import com.farminikagroup.farminika.ui.SectorBottomSheet
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_welcome_screen.*
import kotlinx.android.synthetic.main.bottom_sheet_layout.*
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var sectorViewModel: SectorViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
       sectorViewModel = ViewModelProvider(requireActivity())[SectorViewModel::class.java]

        val lookingButton = view.findViewById<TextView>(R.id.lookingButton)
        lookingButton.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_sectorBottomSheet)
        }
        sectorViewModel.sector.observe(requireActivity()){
            Toast.makeText(requireActivity(), it, Toast.LENGTH_SHORT).show()
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