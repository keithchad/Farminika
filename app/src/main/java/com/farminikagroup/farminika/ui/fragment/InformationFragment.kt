package com.farminikagroup.farminika.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.farminikagroup.farminika.R
import com.google.android.material.button.MaterialButton

class InformationFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_information, container, false)
        initialize(view)
        return view
    }

    private fun initialize(view: View) {
        val submitButton = view.findViewById<MaterialButton>(R.id.submitInfoButton)
        submitButton.setOnClickListener {
            findNavController().navigate(R.id.action_informationFragment_to_messageFragment)
        }
    }

}