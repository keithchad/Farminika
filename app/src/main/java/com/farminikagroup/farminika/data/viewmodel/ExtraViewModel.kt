package com.farminikagroup.farminika.data.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ExtraViewModel : ViewModel() {
    val profession = MutableLiveData<String>()
}