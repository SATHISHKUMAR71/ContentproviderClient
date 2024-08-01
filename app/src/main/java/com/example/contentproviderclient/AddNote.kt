package com.example.contentproviderclient

import android.content.ContentValues
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

    private var cursor: Cursor? = null
    private var noteId=0
    private lateinit var note: Notes
//    private val queryUri =
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    companion object{
        private const val COLUMN_TITLE = "title"
        private const val COLUMN_CONTENT = "content"
        private const val COLUMN_CREATED_AT = "created_at"
        private const val COLUMN_UPDATED_AT = "updated_at"
        private const val COLUMN_IS_PINNED = "is_pinned"
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
                title.setText(it.getString("title"))
                content.setText(it.getString("content"))
                date.text = (it.getString("date"))
                noteId = it.getInt("id")
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
                }
            else{
                note = Notes(noteId,title.text.toString(),content.text.toString(),LocalDate.now().toString(),LocalDate.now().toString(),0)
//                Update Operation
                val contentResolver = requireActivity().contentResolver
                var uri = Uri.parse("content://com.example.databasewithcontentprovider.notescontentprovider/notes_app/${note.id}")

                    val values = ContentValues().apply {
                        put(COLUMN_TITLE,note.title)
                        put(COLUMN_CONTENT,note.content)
                        put(COLUMN_CREATED_AT,note.createdAt)
                        put(COLUMN_IS_PINNED,note.isPinned)
                        put(COLUMN_UPDATED_AT,note.updatedAt)
                    }

                println("updated: ${contentResolver.update(uri,values,null,null)}")
            }
            println((Notes(0,title.text.toString(),content.text.toString(),LocalDate.now().toString(),LocalDate.now().toString(),0)).toString())
            parentFragmentManager.popBackStack()
        }
        view.findViewById<ImageButton>(R.id.deleteNote).setOnClickListener {
//            Delete Operation
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