package com.farminikagroup.farminika.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.bumptech.glide.Glide
import com.farminikagroup.farminika.R
import com.farminikagroup.farminika.data.model.ExpertUser
import com.farminikagroup.farminika.data.model.FarmerUser
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
        lateinit var profession: String

        firebaseAuth = Firebase.auth
        firebaseUser = firebaseAuth.currentUser!!

        databaseReference = FirebaseDatabase.getInstance().getReference("Farmer").child(firebaseUser.uid)
        databaseReference.addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val user: FarmerUser? = snapshot.getValue(FarmerUser::class.java)
                if (user != null) {

                    profession = user.profession
                    if (profession == "Expert") {

                        databaseReference =
                            FirebaseDatabase.getInstance().getReference("Expert").child(firebaseUser.uid)
                        databaseReference.addValueEventListener(object : ValueEventListener {
                            override fun onDataChange(snapshot: DataSnapshot) {
                                val expertUser: ExpertUser? = snapshot.getValue(ExpertUser::class.java)
                                if (expertUser != null) {
                                    textUserName.text = expertUser.userName
                                    textNameProfile.text = expertUser.name
                                    textEmailProfile.text = expertUser.email
                                    textNumberProfile.text = expertUser.phoneNumber
                                    textLocationProfile.text = expertUser.location
                                    textProfession.text = expertUser.profession
                                    Glide.with(context!!).load(expertUser.profileImage).into(userImageProfile)
                                }
                            }

                            override fun onCancelled(error: DatabaseError) {
                                Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show()
                            }

                        })

                    } else {
                        databaseReference =
                            FirebaseDatabase.getInstance().getReference("Farmer").child(firebaseUser.uid)
                        databaseReference.addValueEventListener(object : ValueEventListener {
                            override fun onDataChange(snapshot: DataSnapshot) {
                                val farmerUser: FarmerUser? = snapshot.getValue(FarmerUser::class.java)
                                if (farmerUser != null) {
                                    textUserName.text = farmerUser.userName
                                    textNameProfile.text = farmerUser.name
                                    textEmailProfile.text = farmerUser.email
                                    textNumberProfile.text = farmerUser.phoneNumber
                                    textLocationProfile.text = farmerUser.location
                                    textProfession.text = farmerUser.profession
                                    Glide.with(context!!).load(farmerUser.profileImage).into(userImageProfile)
                                }
                            }

                            override fun onCancelled(error: DatabaseError) {
                                Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show()
                            }

                        })
                    }
                    Toast.makeText(requireActivity(), profession, Toast.LENGTH_SHORT).show()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

}