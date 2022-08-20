package com.farminikagroup.farminika.data.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class UserViewModel : ViewModel() {
    val userId = MutableLiveData<String>()
    val imageProfile = MutableLiveData<String>()
    val userName = MutableLiveData<String>()

}