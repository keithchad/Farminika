package com.farminikagroup.farminika.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.farminikagroup.farminika.R
import com.farminikagroup.farminika.data.viewmodel.InformationViewModel
import com.google.android.material.button.MaterialButton

class InformationFragment : Fragment() {

    private lateinit var informationViewModel: InformationViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_information, container, false)
        initialize(view)
        return view
    }

    private fun initialize(view: View) {
        informationViewModel = ViewModelProvider(requireActivity())[InformationViewModel::class.java]
        val submitButton = view.findViewById<MaterialButton>(R.id.submitInfoButton)
        val budgetEditText = view.findViewById<EditText>(R.id.editTextBudget)
        val descriptionEditText = view.findViewById<EditText>(R.id.editTextDescription)
        val locationEditText = view.findViewById<EditText>(R.id.editTextLocation)
        val jobTitleEditText = view.findViewById<EditText>(R.id.editTextJobtitle)

        informationViewModel.budget.value = budgetEditText.text.toString()
        informationViewModel.description.value = descriptionEditText.text.toString()
        informationViewModel.location.value = locationEditText.text.toString()
        informationViewModel.jobTitle.value = jobTitleEditText.text.toString()


        submitButton.setOnClickListener {
            findNavController().navigate(R.id.action_informationFragment_to_messageFragment)
        }

    }

}