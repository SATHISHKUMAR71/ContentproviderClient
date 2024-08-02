package com.example.contentproviderclient

import android.content.ContentResolver
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import java.time.LocalDate

class AddNote : Fragment() {

    private var noteId=0
    private lateinit var note: Notes
    private lateinit var contentResolver:ContentResolver

    override fun onAttach(context: Context) {
        super.onAttach(context)
        contentResolver = context.contentResolver
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_add_note, container, false)
        val title = view.findViewById<EditText>(R.id.title)
        val content = view.findViewById<EditText>(R.id.content)
        val date = view.findViewById<TextView>(R.id.date)
        date.text = LocalDate.now().toString()
        if(arguments!=null){
            arguments?.let {
                title.setText(it.getString(NotesConstants.COLUMN_TITLE))
                content.setText(it.getString(NotesConstants.COLUMN_CONTENT))
                date.text = (it.getString(NotesConstants.COLUMN_CREATED_AT))
                noteId = it.getInt(NotesConstants.COLUMN_ID)
                note = Notes(noteId,title.text.toString(),content.text.toString(),LocalDate.now().toString(),LocalDate.now().toString(),0)
            }
        }
        view.findViewById<ImageButton>(R.id.backNavigator).setOnClickListener {
            parentFragmentManager.popBackStack()
        }
        view.findViewById<ImageButton>(R.id.save).setOnClickListener {
            if(arguments==null){
                note = Notes(0,title.text.toString(),content.text.toString(),LocalDate.now().toString(),LocalDate.now().toString(),0)
//                Insert Operation

                val values = ContentValues().apply {
                    put(NotesConstants.COLUMN_TITLE,note.title)
                    put(NotesConstants.COLUMN_CONTENT,note.content)
                    put(NotesConstants.COLUMN_CREATED_AT,note.createdAt)
                    put(NotesConstants.COLUMN_IS_PINNED,note.isPinned)
                    put(NotesConstants.COLUMN_UPDATED_AT,note.updatedAt)
                }
                contentResolver.insert(NotesConstants.CONTENT_URI,values)
            }
            else{
                note = Notes(noteId,title.text.toString(),content.text.toString(),LocalDate.now().toString(),LocalDate.now().toString(),0)
//                Update Operation

                val uri = Uri.parse("${NotesConstants.CONTENT_URI}/${note.id}")
                val values = ContentValues().apply {
                    put(NotesConstants.COLUMN_TITLE,note.title)
                    put(NotesConstants.COLUMN_CONTENT,note.content)
                    put(NotesConstants.COLUMN_CREATED_AT,note.createdAt)
                    put(NotesConstants.COLUMN_IS_PINNED,note.isPinned)
                    put(NotesConstants.COLUMN_UPDATED_AT,note.updatedAt)
                }
                println("updated: ${contentResolver.update(uri,values,null,null)}")
            }
            println((Notes(0,title.text.toString(),content.text.toString(),LocalDate.now().toString(),LocalDate.now().toString(),0)).toString())
            parentFragmentManager.popBackStack()
        }
        view.findViewById<ImageButton>(R.id.deleteNote).setOnClickListener {
//            Delete Operation

            val uri = Uri.parse("${NotesConstants.CONTENT_URI}/${note.id}")
            contentResolver.delete(uri,null,null)
            parentFragmentManager.popBackStack()
            Toast.makeText(context,"Notes Deleted Successfully",Toast.LENGTH_SHORT).show()
        }

        return view
    }

    override fun onDestroy() {
        super.onDestroy()
        println("On Fragment Destroy")
    }
}