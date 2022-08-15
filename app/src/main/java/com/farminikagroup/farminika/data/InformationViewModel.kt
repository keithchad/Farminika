package com.farminikagroup.farminika.data

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class InformationViewModel : ViewModel() {
    val jobTitle = MutableLiveData<String>()
    val description = MutableLiveData<String>()
    val budget = MutableLiveData<Int>()
    val location = MutableLiveData<String>()
}