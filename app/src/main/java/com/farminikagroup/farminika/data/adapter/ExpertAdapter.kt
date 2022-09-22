package com.farminikagroup.farminika.data.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.farminikagroup.farminika.R
import com.farminikagroup.farminika.data.model.Expert
import kotlinx.android.synthetic.main.expert_list_item.view.*

class ExpertAdapter(var list: List<Expert>, var context: Context) : RecyclerView.Adapter<ExpertAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(context).inflate(R.layout.expert_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val expert: Expert = list[position]
        holder.setData(expert)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        lateinit var navController: NavController
        fun setData(expert: Expert) {
            Glide.with(context).load(expert.profileImage).into(itemView.profileImageExpert)
            itemView.textUserNameExpert.text = expert.userName
            itemView.textNameExpert.text = expert.name
            itemView.textNameExpert.setOnClickListener {
                navController = Navigation.findNavController(itemView)
                navController.navigate(R.id.action_expertFragment_to_informationFragment)
            }
        }

    }
}