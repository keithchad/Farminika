package com.farminikagroup.farminika

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.junit.runner.RunWith

class FirebaseUnitTest {

    private lateinit var firebaseAuth: FirebaseAuth
    private var firebaseUser: FirebaseUser? = null

    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Before
    fun initializeFirebase() {
        firebaseAuth = Firebase.auth
        firebaseUser = firebaseAuth.currentUser
    }

    @Test
    fun test_if_email_is_correct() {
        val expectedEmail = "keithchad6@gmail.com"
        val actualEmail: String? = firebaseUser?.email

        assertEquals(expectedEmail,  actualEmail)

    }
}