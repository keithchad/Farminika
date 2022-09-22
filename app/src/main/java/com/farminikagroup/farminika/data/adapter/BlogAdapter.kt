package com.farminikagroup.farminika.data.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.farminikagroup.farminika.R
import com.farminikagroup.farminika.data.model.Blog
import kotlinx.android.synthetic.main.blog_item.view.*

class BlogAdapter(var list: List<Blog>, var context: Context) : RecyclerView.Adapter<BlogAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.blog_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val blog = list[position]
        holder.setData(blog)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun setData(blog: Blog) {
            Glide.with(context).load(blog.blogImage).into(itemView.blogImage)
            itemView.textBlogTitle.text = blog.blogTitle
            Glide.with(context).load(blog.blogProfileImage).into(itemView.blogImageProfile)
            itemView.textBlogUserName.text = blog.blogUserName
            itemView.blogReadTime.text = blog.readTime
        }
    }
}