package me.bkkn.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import me.bkkn.App
import me.bkkn.databinding.ActivityMainBinding
import me.bkkn.domain.entity.Note
import me.bkkn.domain.repository.Notes
import me.bkkn.ui.list.NoteAdapter
import me.bkkn.ui.list.NoteViewHolder.OnNoteListener

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var notes: Notes? = null
    private var adapter: NoteAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)
        notes = App.get(this).notes
        initRecycler()
    }

    private fun initRecycler() {
        binding.addNoteButton.setOnClickListener {
            notes!!.addNewNote()
            val list = notes!!.notes
            adapter!!.setData(list)
            adapter!!.notifyItemInserted(list.size - 1)
            binding.recyclerView.smoothScrollToPosition(list.size - 1)
        }
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = NoteAdapter()
        val list = notes!!.notes
        adapter!!.setData(list)
        adapter!!.setOnDeleteClickListener(object : OnNoteListener {
            override fun onDeleteNote(note: Note) {
                notes!!.deleteNote(note)
                adapter!!.setData(notes!!.notes)
            }

            override fun onClickNote(note: Note) {
                val intent = Intent(this@MainActivity, NoteActivity::class.java)
                intent.putExtra(NoteActivity.NOTE_EXTRA_KEY, note)
                startActivityForResult(intent, NOTE_REQUEST_CODE)
            }
        })
        binding.recyclerView.setAdapter(adapter)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == NOTE_REQUEST_CODE && resultCode == RESULT_OK) {
            adapter!!.setData(notes!!.notes)
            //Note note = data.getParcelableExtra(NOTE_EXTRA_KEY);
            //int idx = App.get(this).getNotes().index(note);
            adapter!!.notifyDataSetChanged()
        }
    }

    companion object {
        private const val NOTE_REQUEST_CODE = 42
    }
}