package com.farminikagroup.farminika.data.utils

import android.app.Activity
import android.app.Fragment
import android.content.Context
import android.widget.Toast

object Extensions {
    fun Context.toast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}