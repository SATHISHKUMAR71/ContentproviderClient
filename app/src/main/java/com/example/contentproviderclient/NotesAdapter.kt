package com.example.contentproviderclient

import android.content.ContentResolver
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView


class NotesAdapter(private var activity: FragmentActivity):RecyclerView.Adapter<NotesAdapter.NotesViewHolder>() {
    private var notesList: MutableList<Notes> = mutableListOf()

    inner class NotesViewHolder(itemView: View):RecyclerView.ViewHolder(itemView)
    init {
        val cursor = activity.contentResolver.query(NotesConstants.CONTENT_URI,null,null,null,null)
        println(cursor)
        cursor?.use {
            while (it.moveToNext()){
                notesList.add(
                    Notes(
                        id = it.getInt(it.getColumnIndexOrThrow(NotesConstants.COLUMN_ID)),
                        title = it.getString(it.getColumnIndexOrThrow(NotesConstants.COLUMN_TITLE)),
                        content = it.getString(it.getColumnIndexOrThrow(NotesConstants.COLUMN_CONTENT)),
                        createdAt = it.getString(it.getColumnIndexOrThrow(NotesConstants.COLUMN_CREATED_AT)),
                        updatedAt = it.getString(it.getColumnIndexOrThrow(NotesConstants.COLUMN_UPDATED_AT)),
                        isPinned = it.getInt(it.getColumnIndexOrThrow(NotesConstants.COLUMN_IS_PINNED))
                    ))
                println(notesList.toString())
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {

        return NotesViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.notes_layout,parent,false))
    }

    override fun getItemCount(): Int {
        return notesList.size
    }

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
        holder.itemView.apply {
            findViewById<TextView>(R.id.titleNote).text = notesList[position].title
            findViewById<TextView>(R.id.dateNote).text = notesList[position].createdAt
            findViewById<TextView>(R.id.contentNote).text = notesList[position].content
            this.setOnClickListener {
                val addNoteFragment = AddNote()
                addNoteFragment.arguments = Bundle().apply {
                    putInt(NotesConstants.COLUMN_ID,notesList[position].id)
                    putString(NotesConstants.COLUMN_TITLE,notesList[position].title)
                    putString(NotesConstants.COLUMN_CREATED_AT,notesList[position].createdAt)
                    putString(NotesConstants.COLUMN_CONTENT,notesList[position].content)
                }
                Toast.makeText(context,"Message Clicked",Toast.LENGTH_SHORT).show()
                activity.supportFragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainerView,addNoteFragment)
                    .addToBackStack("Note View")
                    .commit()
            }
            findViewById<ImageButton>(R.id.deleteBtnList).setOnClickListener {
                val contentResolver = activity.contentResolver
                val uri = Uri.parse("${NotesConstants.CONTENT_URI}/${notesList[position].id}")
                contentResolver.delete(uri,null,null)
                notesList.removeAt(position)
                notifyItemRemoved(position)
            }
        }
    }
}