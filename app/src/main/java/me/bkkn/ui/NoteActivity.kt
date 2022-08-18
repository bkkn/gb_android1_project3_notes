package me.bkkn.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import me.bkkn.App
import me.bkkn.databinding.ActivityNoteBinding
import me.bkkn.domain.entity.Note

class NoteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNoteBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val intent = intent
        val note = intent.getParcelableExtra<Note>(NOTE_EXTRA_KEY)
        binding.nameTextView.setText(note!!.title)
        binding.contentTextView.setText(note.text)
        binding.deleteButton.setOnClickListener(View.OnClickListener { v: View? ->
            App.get(this).notes.deleteNote(note)
            setResult(RESULT_OK)
            finish()
        })
        binding.cancelButton.setOnClickListener(View.OnClickListener { v: View? ->
            setResult(RESULT_CANCELED)
            finish()
        })
        binding.okButton.setOnClickListener(View.OnClickListener { v: View? ->
            App.get(this).notes.editNote(
                note,
                binding.nameTextView.text.toString(),
                binding.contentTextView.text.toString()
            )
            setResult(RESULT_OK)
            finish()
        })
    }

    companion object {
        const val NOTE_EXTRA_KEY = "NOTE_EXTRA_KEY"
    }
}