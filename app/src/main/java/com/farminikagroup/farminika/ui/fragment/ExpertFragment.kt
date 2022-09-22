package com.farminikagroup.farminika.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.farminikagroup.farminika.R
import com.farminikagroup.farminika.data.viewmodel.SectorViewModel
import com.farminikagroup.farminika.data.adapter.ExpertAdapter
import com.farminikagroup.farminika.data.model.Expert
import com.farminikagroup.farminika.data.utils.Constants
import com.farminikagroup.farminika.data.utils.PreferenceManager
import com.farminikagroup.farminika.data.viewmodel.UserViewModel
import com.farminikagroup.farminika.databinding.FragmentExpertBinding
import com.farminikagroup.farminika.databinding.FragmentInformationBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.fragment_expert.*


class ExpertFragment : Fragment() {

    private lateinit var fragmentExpertBinding: FragmentExpertBinding
    private val binding get() = fragmentExpertBinding

    lateinit var list: ArrayList<Expert>
    private lateinit var sectorViewModel: SectorViewModel
    private lateinit var userViewModel: UserViewModel

    private lateinit var preferenceManager: PreferenceManager

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeRecyclerView()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragmentExpertBinding = FragmentExpertBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun initializeRecyclerView() {

        list = ArrayList()
        val expertAdapter = ExpertAdapter(list, requireActivity())
        preferenceManager = PreferenceManager(requireActivity())

        binding.recyclerViewExpert.layoutManager = LinearLayoutManager(requireActivity())
        binding.recyclerViewExpert.adapter = expertAdapter
        binding.recyclerViewExpert.setHasFixedSize(true)

        swipeRecyclerView.isRefreshing = true
        swipeRecyclerView.setOnRefreshListener {
            retrieveDataFromFirebase(expertAdapter)
        }

        retrieveDataFromFirebase(expertAdapter)
    }

    private fun retrieveDataFromFirebase(expertAdapter: ExpertAdapter) {
        userViewModel = ViewModelProvider(requireActivity())[UserViewModel::class.java]

        val reference = FirebaseDatabase.getInstance().reference.child("Expert")

        reference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                swipeRecyclerView.isRefreshing = false

                list.clear()

                for (dataSnapshot in snapshot.children) {
                    val expert: Expert = dataSnapshot.getValue(Expert::class.java)!!
                    list.add(expert)
                    userViewModel.userId.value = expert.id
                    userViewModel.userName.value = expert.userName
                    userViewModel.imageProfile.value = expert.profileImage
                    preferenceManager.putString(Constants.KEY_PREFERENCE_USER_ID, expert.id)
                    Toast.makeText(activity, expert.profileImage, Toast.LENGTH_SHORT).show()
                }

                expertAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })

        sectorViewModel = ViewModelProvider(requireActivity())[SectorViewModel::class.java]

        sectorViewModel.sector.observe(requireActivity()){
            binding.textSectorCustom.text = it
        }
    }

}