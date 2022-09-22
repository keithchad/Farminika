package com.farminikagroup.farminika.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.farminikagroup.farminika.R
import com.farminikagroup.farminika.data.viewmodel.InformationViewModel
import com.farminikagroup.farminika.databinding.FragmentInformationBinding

class InformationFragment : Fragment() {

    private lateinit var fragmentInformationBinding: FragmentInformationBinding
    private val binding get() = fragmentInformationBinding
    private lateinit var informationViewModel: InformationViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialize()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragmentInformationBinding = FragmentInformationBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun initialize() {

        informationViewModel = ViewModelProvider(requireActivity())[InformationViewModel::class.java]

        binding.submitInfoButton.setOnClickListener {
            informationViewModel.budget.value = binding.editTextBudget.text.toString()
            informationViewModel.description.value = binding.editTextDescription.text.toString()
            informationViewModel.location.value = binding.editTextLocation.text.toString()
            informationViewModel.jobTitle.value = binding.editTextJobtitle.text.toString()
            findNavController().navigate(R.id.action_informationFragment_to_messageFragment)
        }

    }

}