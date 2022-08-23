package com.farminikagroup.farminika.ui.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.farminikagroup.farminika.R
import com.farminikagroup.farminika.data.model.ExpertUser
import com.farminikagroup.farminika.data.model.FarmerUser
import com.farminikagroup.farminika.data.model.Users
import com.farminikagroup.farminika.ui.activity.IntroScreenActivity
import com.farminikagroup.farminika.ui.activity.MainActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_profile.*

class ProfileFragment : Fragment() {

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var firebaseUser: FirebaseUser
    private lateinit var databaseReference: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        //signOutUser()
        retrieveDataFromFirebase()

        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    private fun retrieveDataFromFirebase() {
        lateinit var profession: String

        firebaseAuth = Firebase.auth
        firebaseUser = firebaseAuth.currentUser!!

        databaseReference =
            FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.uid)
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val allUsers: Users? = snapshot.getValue(Users::class.java)
                if (allUsers != null) {
                    textUserName.text = allUsers.userName
                    textNameProfile.text = allUsers.name
                    textEmailProfile.text = allUsers.email
                    textNumberProfile.text = allUsers.mobileNumber
                    textLocationProfile.text = allUsers.location
                    textProfession.text = allUsers.profession
                    Glide.with(context!!).load(allUsers.imageProfile).into(userImageProfile)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show()
            }

        })
    }

    private fun signOutUser() {

        firebaseAuth = Firebase.auth

        signOutButton.setOnClickListener {
            firebaseAuth.signOut()
            val intent = Intent(requireActivity(), IntroScreenActivity::class.java)
            startActivity(intent)
        }
    }

}