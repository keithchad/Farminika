package com.farminikagroup.farminika.data.auth

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.farminikagroup.farminika.R
import com.farminikagroup.farminika.data.model.ExpertUser
import com.farminikagroup.farminika.data.model.FarmerUser
import com.farminikagroup.farminika.data.utils.Constants
import com.farminikagroup.farminika.data.utils.Extensions.toast
import com.farminikagroup.farminika.ui.activity.MainActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_sign_up_screen.*

class SignUpScreen : AppCompatActivity() {

    //Prepare variables for late initialization
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var firebaseUser: FirebaseUser
    private lateinit var databaseReference: DatabaseReference
    private var profession: String? = null
    private lateinit var dropDownList: List<String>
    private var sector: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up_screen)
        setUpDropDownList()
        initializeFirebase()
    }

    //Initialize Firebase
    private fun initializeFirebase() {

        profession = intent.getStringExtra(Constants.INTENT_EXTRA_PROFESSION)

        if (profession != null) {

            if (profession == Constants.INTENT_EXTRA_FARMER) {
                inputLayoutEducation.visibility = View.GONE
                inputLayoutExpertise.visibility = View.GONE
            }
        } else {
            toast("Profession is Null")
        }

        textSignIn.setOnClickListener {
            val intent = Intent(this, SignInScreen::class.java)
            startActivity(intent)
        }
        firebaseAuth = Firebase.auth

        signupButton.setOnClickListener {
            signUpProgressBar.visibility = View.VISIBLE
            signupButton.visibility = View.INVISIBLE
            performSignIn()
        }

    }

    //Sign Up using Email and Password
    private fun performSignIn() {
        val email = editTextEmail.text.toString()
        val password = editTextPassword.text.toString()

        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) {

                signUpProgressBar.visibility = View.INVISIBLE
                signupButton.visibility = View.VISIBLE

                if(it.isSuccessful) {

                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()

                    sendUserDataToFirebase()
                    sendEmailVerification(email)
                } else {
                    toast("Failed to Sign Up")
                }
            }.addOnFailureListener(this) {
                toast(it.toString())
            }

    }

    //Send User Data to Firebase Database
    private fun sendUserDataToFirebase() {
        firebaseUser = firebaseAuth.currentUser!!
        val userId = firebaseUser.uid
        val userName = "user01"
        val name = editTextName.text.toString()
        val email = editTextEmail.text.toString()
        val education = editTextEducation.text.toString()
        val sector = sector
        val location = "Unknown"
        val phoneNumber = "Empty"
        val profileImage = ""
        val profession = profession

        if(profession == Constants.INTENT_EXTRA_FARMER) {
            databaseReference = FirebaseDatabase.getInstance().getReference("Farmer").child(userId)

            val user = FarmerUser(userId, profileImage, name, userName,
                email, location, phoneNumber, profession)

            databaseReference.setValue(user).addOnSuccessListener {
                editTextName.text.clear()
                editTextEmail.text.clear()
                toast("Data has been successfully saved")
            }. addOnFailureListener{
                toast("Failed to saved Data")
            }

        } else if(profession == Constants.INTENT_EXTRA_EXPERT) {
            databaseReference = FirebaseDatabase.getInstance().getReference("Expert").child(userId)

            val user = ExpertUser(userId, profileImage, name, userName, email,
                location, phoneNumber, sector!!, education, profession)

            databaseReference.setValue(user).addOnSuccessListener {
                editTextName.text.clear()
                editTextEmail.text.clear()
                toast("Data has been successfully saved")
            }. addOnFailureListener{
                toast("Failed to saved Data")
            }
        }


    }

    //Send Email Verification to User Email
    private fun sendEmailVerification(email: String) {

        firebaseUser = firebaseAuth.currentUser!!
        firebaseUser.sendEmailVerification().addOnCompleteListener(this) {
            if (it.isSuccessful) {
                toast("Email has been sent to $email")
            }
        }
    }

    private fun setUpDropDownList() {
        dropDownList = listOf(
            "Fish Farming",
            "Horticulture",
            "Bee Keeping",
            "Tea & Coffee",
            "Grain Farming",
            "Fertilizers and Pesticides"
        )
        val adapterItems: ArrayAdapter<String> = ArrayAdapter(this,
            R.layout.drop_down_list_item, dropDownList)
        dropdownSector.setAdapter(adapterItems)
        dropdownSector.setOnItemClickListener { parent, _, position, _ ->
            sector = parent.getItemAtPosition(position).toString()
        }
    }

    private fun getProfession() {
        //instantiate Viewmodel
//        extraViewModel = ViewModelProvider(this)[ExtraViewModel::class.java]
//        extraViewModel.profession.observe(requireActivity()) {
//            if (it != null) {
//                Toast.makeText(requireActivity(), it, Toast.LENGTH_SHORT).show()
//            }else {
//                Toast.makeText(requireActivity(), "profession is null", Toast.LENGTH_SHORT).show()
//            }
//            //profession = it
//        }
    }

}