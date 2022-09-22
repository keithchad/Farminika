package com.farminikagroup.farminika.data.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class InformationViewModel : ViewModel() {
    val jobTitle = MutableLiveData<String>()
    val description = MutableLiveData<String>()
    val budget = MutableLiveData<String>()
    val location = MutableLiveData<String>()
}