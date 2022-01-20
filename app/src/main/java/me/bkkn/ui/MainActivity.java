package me.bkkn.ui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import me.bkkn.R;
import me.bkkn.domain.entity.Note;
import me.bkkn.ui.details.NoteDetailsFragment;
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
    public void popBackFragment() {
        getSupportFragmentManager().popBackStack();
    }
}