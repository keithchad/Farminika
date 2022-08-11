package com.farminikagroup.farminika.data

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ExtraViewModel : ViewModel() {
    val profession = MutableLiveData<String>()
}