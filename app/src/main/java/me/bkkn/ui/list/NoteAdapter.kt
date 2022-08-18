package me.bkkn.ui.list

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import me.bkkn.domain.entity.Note
import me.bkkn.ui.list.NoteViewHolder.OnNoteListener

class NoteAdapter : RecyclerView.Adapter<NoteViewHolder>() {
    private var data: MutableList<Note> = mutableListOf()
    private lateinit var onNoteListener: OnNoteListener
    fun setOnDeleteClickListener(onNoteListener: OnNoteListener) {
        this.onNoteListener = onNoteListener
    }

    fun setData(notes: MutableList<Note>) {
        data = notes
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val inflater =
            parent.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        return NoteViewHolder(inflater, parent, onNoteListener)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    private fun getItem(position: Int): Note {
        return data[position]
    }

    override fun getItemCount(): Int {
        return data.size
    }
}