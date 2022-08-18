package me.bkkn.ui.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import me.bkkn.R
import me.bkkn.domain.entity.Note

class NoteViewHolder(
    inflater: LayoutInflater,
    parent: ViewGroup,
    private val onNoteListener: OnNoteListener
) : RecyclerView.ViewHolder(inflater.inflate(R.layout.item_note, parent, false)) {
    private val nameTextView = itemView.findViewById<TextView>(R.id.title_text_view)
    private val contentTextView = itemView.findViewById<TextView>(R.id.content_text_view)
    private val deleteButton = itemView.findViewById<ImageButton>(R.id.delete_button)
    fun bind(note: Note) {
        deleteButton.setOnClickListener { v: View? -> onNoteListener.onDeleteNote(note) }
        itemView.setOnClickListener { v: View? -> onNoteListener.onClickNote(note) }
        nameTextView.text = note.title
        contentTextView.text = note.text
    }

    interface OnNoteListener {
        fun onDeleteNote(note: Note)
        fun onClickNote(note: Note)
    }
}