package me.bkkn.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import me.bkkn.App;
import me.bkkn.R;
import me.bkkn.domain.entity.Note;
import me.bkkn.domain.repository.Notes;
import me.bkkn.ui.details.NoteDetailsFragment;
import me.bkkn.ui.list.NoteAdapter;
import me.bkkn.ui.list.NoteViewHolder;
import me.bkkn.ui.list.NotesFragment;

public class MainActivity
        extends AppCompatActivity
        implements NotesFragment.Controller, NoteDetailsFragment.Controller {

    private static final String TAG_LIST_FRAGMENT = "TAG_LIST_FRAGMENT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Fragment notesFragment = new NotesFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.activity_main__main_fragment_container, notesFragment, TAG_LIST_FRAGMENT)
                .commit();
    }

    @Override
    public void showNoteDetails(Note note) {
        Fragment noteDetailsFragment = NoteDetailsFragment.newInstance(note);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.activity_main__main_fragment_container, noteDetailsFragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onDeleteNote(String noteId) {
        getSupportFragmentManager().popBackStack();
        NotesFragment notesFragment = (NotesFragment) getSupportFragmentManager().findFragmentByTag(TAG_LIST_FRAGMENT);
        if (notesFragment == null)
            throw new IllegalStateException("NotesFragment not on screen");
        notesFragment.onDeleteNote(noteId);
    }

    @Override
    public void onCloseNote(String noteId) {
        getSupportFragmentManager().popBackStack();
        NotesFragment notesFragment = (NotesFragment) getSupportFragmentManager().findFragmentByTag(TAG_LIST_FRAGMENT);
        if (notesFragment == null)
            throw new IllegalStateException("NotesFragment not on screen");
        notesFragment.onCloseNote(noteId);
    }

    @Override
    public void onSaveNote(String noteId, String newTitle, String newContent) {
        getSupportFragmentManager().popBackStack();
        NotesFragment notesFragment = (NotesFragment) getSupportFragmentManager().findFragmentByTag(TAG_LIST_FRAGMENT);
        if (notesFragment == null)
            throw new IllegalStateException("NotesFragment not on screen");
        notesFragment.onSaveNote(noteId);
    }
}