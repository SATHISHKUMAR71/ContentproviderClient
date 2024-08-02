package com.example.contentproviderclient

import android.net.Uri

class NotesConstants {
        companion object{
            private const val AUTHORITY = "com.example.databasewithcontentprovider.notescontentprovider"
            private const val PATH_NOTES = "notes_app"
            val CONTENT_URI: Uri = Uri.parse("content://$AUTHORITY/$PATH_NOTES")
            const val COLUMN_ID = "id"
            const val COLUMN_TITLE = "title"
            const val COLUMN_CONTENT = "content"
            const val COLUMN_CREATED_AT = "created_at"
            const val COLUMN_UPDATED_AT = "updated_at"
            const val COLUMN_IS_PINNED = "is_pinned"
        }
}

