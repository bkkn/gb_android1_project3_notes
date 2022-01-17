package me.bkkn.ui;

import static me.bkkn.ui.NoteActivity.NOTE_EXTRA_KEY;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import me.bkkn.App;
import me.bkkn.R;
import me.bkkn.domain.entity.Note;
import me.bkkn.domain.repository.Notes;

public class MainActivity extends AppCompatActivity {
    private static final int NOTE_REQUEST_CODE = 42;

    private Notes notes;

    private RecyclerView recyclerView;
    private NoteAdapter adapter;

    private Button addNewNoteButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        notes = App.get(this).getNotes();

        initRecycler();
    }

    private void initRecycler() {
        recyclerView = findViewById(R.id.recycler_view);
        addNewNoteButton = findViewById(R.id.add_note_button);

        addNewNoteButton.setOnClickListener(v -> {
            notes.addNewNote();
            List<Note> list = notes.getNotes();
            adapter.setData(list);
            adapter.notifyItemInserted(list.size() - 1);
            recyclerView.smoothScrollToPosition(list.size() - 1);
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new NoteAdapter();
        List<Note> list = notes.getNotes();
        adapter.setData(list);
        adapter.setOnDeleteClickListener(new NoteViewHolder.OnNoteListener() {
            @Override
            public void onDeleteNote(Note note) {
                notes.deleteNote(note);
                adapter.setData(notes.getNotes());
            }

            @Override
            public void onClickNote(Note note) {
                Intent intent = new Intent(MainActivity.this, NoteActivity.class);
                intent.putExtra(NOTE_EXTRA_KEY, note);
                startActivityForResult(intent, NOTE_REQUEST_CODE);
            }
        });

        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == NOTE_REQUEST_CODE && resultCode == RESULT_OK) {
            adapter.setData(notes.getNotes());
            //Note note = data.getParcelableExtra(NOTE_EXTRA_KEY);
            //int idx = App.get(this).getNotes().index(note);
            adapter.notifyDataSetChanged();
        }
    }
}