package com.farminikagroup.farminika.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.farminikagroup.farminika.R
import com.farminikagroup.farminika.data.SectorViewModel
import com.farminikagroup.farminika.data.adapter.ExpertAdapter
import com.farminikagroup.farminika.data.model.Expert
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.fragment_expert.*


class ExpertFragment : Fragment() {

    lateinit var list: ArrayList<Expert>
    private lateinit var sectorViewModel: SectorViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_expert, container, false)
        initializeRecyclerView(view)
        return view
    }

    private fun initializeRecyclerView(view: View) {
        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerViewExpert)
        val swipeRecyclerView = view.findViewById<SwipeRefreshLayout>(R.id.swipeRecyclerView)

        list = ArrayList()
        val expertAdapter = ExpertAdapter(list, requireActivity())

        recyclerView.layoutManager = LinearLayoutManager(requireActivity())
        recyclerView.adapter = expertAdapter
        recyclerView.setHasFixedSize(true)

        swipeRecyclerView.isRefreshing = true
        swipeRecyclerView.setOnRefreshListener {
            retrieveDataFromFirebase(expertAdapter, view)
        }

        retrieveDataFromFirebase(expertAdapter, view)
    }

    private fun retrieveDataFromFirebase(expertAdapter: ExpertAdapter, view: View) {
        val reference = FirebaseDatabase.getInstance().reference.child("Expert")

        reference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                swipeRecyclerView.isRefreshing = false

                list.clear()

                for (dataSnapshot in snapshot.children) {
                    val expert: Expert = dataSnapshot.getValue(Expert::class.java)!!
                    list.add(expert)
                }

                expertAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })

        sectorViewModel = ViewModelProvider(requireActivity())[SectorViewModel::class.java]

        sectorViewModel.sector.observe(requireActivity()){
            val textSectorCustom = view.findViewById<TextView>(R.id.textSectorCustom)
            textSectorCustom.text = it
        }
    }

}