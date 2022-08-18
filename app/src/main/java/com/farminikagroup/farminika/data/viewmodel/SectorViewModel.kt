package com.farminikagroup.farminika.data.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SectorViewModel : ViewModel() {
    var sector = MutableLiveData<String>()
}