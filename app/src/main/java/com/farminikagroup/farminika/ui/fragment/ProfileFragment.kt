package com.farminikagroup.farminika.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.bumptech.glide.Glide
import com.farminikagroup.farminika.R
import com.farminikagroup.farminika.data.model.User
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
        retrieveDataFromFirebase()
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    private fun retrieveDataFromFirebase() {
        firebaseAuth = Firebase.auth
        firebaseUser = firebaseAuth.currentUser!!

        databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.uid)
        databaseReference.addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val user: User? = snapshot.getValue(User::class.java)
                if (user != null) {
                    textUserName.text = user.userName
                    textNameProfile.text = user.name
                    textEmailProfile.text = user.email
                    textNumberProfile.text = user.phoneNumber
                    textLocationProfile.text = user.location
                    Glide.with(context!!).load(user.profileImage).into(userImageProfile)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show()
            }

        })
    }

}