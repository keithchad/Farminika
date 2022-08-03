package com.farminikagroup.farminika.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.farminikagroup.farminika.R
import com.farminikagroup.farminika.data.adapter.BlogAdapter
import com.farminikagroup.farminika.data.model.Blog
import kotlinx.android.synthetic.main.fragment_blog.*

class BlogFragment : Fragment() {
    lateinit var blog: List<Blog>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        populateData()
        initializeRecyclerView()
        return inflater.inflate(R.layout.fragment_blog, container, false)
    }

    private fun initializeRecyclerView() {
        val blogAdapter = BlogAdapter(list, context)
        recyclerView.layoutManager = LinearLayoutManager
        recyclerView.adapter = blogAdapter
    }

    private fun populateData() {
         blog = listOf(
            Blog(
                1,
                R.drawable.dummy_photo_1,
                "Welcome to Kenya for the best experience!",
                R.drawable.dummy_photo,
                "Keith Chad",
                "20 Minutes"
            ),
            Blog(
                1,
                R.drawable.dummy_photo_1,
                "Welcome to Kenya for the best experience!",
                R.drawable.dummy_photo,
                "Keith Chad",
                "20 Minutes"
            ),
            Blog(
                1,
                R.drawable.dummy_photo_1,
                "Welcome to Kenya for the best experience!",
                R.drawable.dummy_photo,
                "Keith Chad",
                "20 Minutes"
            ),
            Blog(
                1,
                R.drawable.dummy_photo_1,
                "Welcome to Kenya for the best experience!",
                R.drawable.dummy_photo,
                "Keith Chad",
                "20 Minutes"
            ),
            Blog(
                1,
                R.drawable.dummy_photo_1,
                "Welcome to Kenya for the best experience!",
                R.drawable.dummy_photo,
                "Keith Chad",
                "20 Minutes"
            ),
            Blog(
                1,
                R.drawable.dummy_photo_1,
                "Welcome to Kenya for the best experience!",
                R.drawable.dummy_photo,
                "Keith Chad",
                "20 Minutes"
            ),
            Blog(
                1,
                R.drawable.dummy_photo_1,
                "Welcome to Kenya for the best experience!",
                R.drawable.dummy_photo,
                "Keith Chad",
                "20 Minutes"
            ),
            Blog(
                1,
                R.drawable.dummy_photo_1,
                "Welcome to Kenya for the best experience!",
                R.drawable.dummy_photo,
                "Keith Chad",
                "20 Minutes"
            )

        )
    }

}