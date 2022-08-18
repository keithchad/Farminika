package com.farminikagroup.farminika.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.farminikagroup.farminika.R
import com.farminikagroup.farminika.data.viewmodel.InformationViewModel
import com.farminikagroup.farminika.data.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.fragment_message.*

class MessageFragment : Fragment() {

    private lateinit var informationViewModel: InformationViewModel
    private lateinit var userViewModel: UserViewModel

    private lateinit var jobTitle: String
    private lateinit var budget: String
    private lateinit var location: String
    private lateinit var description: String
    private lateinit var imageProfile: String
    private lateinit var userName: String


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        informationViewModel = ViewModelProvider(requireActivity())[InformationViewModel::class.java]
        userViewModel = ViewModelProvider(requireActivity())[UserViewModel::class.java]

        informationViewModel.budget.observe(requireActivity()){
            budget = it
        }
        informationViewModel.jobTitle.observe(requireActivity()){
            jobTitle = it
        }
        informationViewModel.location.observe(requireActivity()){
            location = it
        }
        informationViewModel.description.observe(requireActivity()){
            description = it
        }

        userViewModel.imageProfile.observe(requireActivity()){
            imageProfile = it
        }
        userViewModel.userName.observe(requireActivity()){
            userName = it
        }

        Toast.makeText(activity, budget + jobTitle + location + description, Toast.LENGTH_SHORT).show()
        Glide.with(requireActivity()).load(imageProfile).into(imageProfileMessage)
        textUserNameMessage.text = userName

        return inflater.inflate(R.layout.fragment_message, container, false)
    }

}