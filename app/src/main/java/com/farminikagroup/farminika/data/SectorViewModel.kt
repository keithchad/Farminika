package com.farminikagroup.farminika.data

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SectorViewModel : ViewModel() {
    var sector = MutableLiveData<String>()
}