package com.farminikagroup.farminika.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.farminikagroup.farminika.R
import com.farminikagroup.farminika.data.adapter.BlogAdapter
import com.farminikagroup.farminika.data.model.Blog

class BlogFragment : Fragment() {

    lateinit var blog: List<Blog>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_blog, container, false)
        // Inflate the layout for this fragment
        populateData()
        initializeRecyclerView(view)

        return view
    }

    private fun initializeRecyclerView(view: View) {
        val blogAdapter = BlogAdapter(blog, requireActivity())
        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerViewBlog)
        recyclerView.adapter = blogAdapter
        recyclerView.layoutManager = LinearLayoutManager(activity)
    }

    private fun populateData() {
        blog = arrayListOf(
            Blog(
                1,
                R.drawable.dummy_photo_2,
                "Village chicken production and food security",
                R.drawable.dummy_photo,
                "John Doe",
                "8 Minutes"
            ),
            Blog(
                1,
                R.drawable.dummy_photo_6,
                "Agriculture and food economics - Springer Open",
                R.drawable.dummy_photo_3,
                "Keith Chad",
                "7 Minutes"
            ),
            Blog(
                1,
                R.drawable.dummy_photo_2,
                "Successful Farming | Agriculture at its best",
                R.drawable.dummy_photo_1,
                "Bellah Oyucho",
                "12 Minutes"
            ),
            Blog(
                1,
                R.drawable.dummy_photo_4,
                "Technology: The future is Agriculture",
                R.drawable.dummy_photo,
                "Jane Doe",
                "11 Minutes"
            ),
            Blog(
                1,
                R.drawable.dummy_photo_5,
                "Agricultural Systems | Journal | Science Direct",
                R.drawable.dummy_photo_1,
                "Felicity Orville",
                "4 Minutes"
            ),
            Blog(
                1,
                R.drawable.dummy_photo_6,
                "Historical Agriculture | History of Agriculture",
                R.drawable.dummy_photo_3,
                "Nancy Shiru",
                "10 Minutes"
            ),
            Blog(
                1,
                R.drawable.dummy_photo_4,
                "Organic Phosphate Insecticides",
                R.drawable.dummy_photo_2,
                "Jack O'Neal",
                "3 Minutes"
            ),
            Blog(
                1,
                R.drawable.dummy_photo_6,
                "Introduction to Modern Agriculture",
                R.drawable.dummy_photo_1,
                "Billy Wash",
                "20 Minutes"
            )

        ) as ArrayList<Blog>
    }

}