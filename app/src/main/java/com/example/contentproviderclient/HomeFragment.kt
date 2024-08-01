package com.example.contentproviderclient

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.contentproviderclient.AddNote
import com.google.android.material.floatingactionbutton.FloatingActionButton

class HomeFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        val rv = view.findViewById<RecyclerView>(R.id.notesRecyclerView)
        rv.adapter = NotesAdapter(requireActivity())
        rv.layoutManager = LinearLayoutManager(context)
        view.findViewById<FloatingActionButton>(R.id.addButton).apply {
            setOnClickListener {
                parentFragmentManager.beginTransaction()
                    .addToBackStack("Add Note")
                    .replace(R.id.fragmentContainerView, AddNote())
                    .commit()
            }
        }
        return view
    }


}