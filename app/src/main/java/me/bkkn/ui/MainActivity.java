package me.bkkn.ui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import java.util.List;

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

        if (savedInstanceState == null) {
            showListInMainContainer();
        }
    }

    private void showListInMainContainer() {
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
                .replace(R.id.activity_main__second_fragment_container, noteDetailsFragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void popBackFragment() {
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void updateDataSet() {
        NotesFragment notesFragment = (NotesFragment) getSupportFragmentManager().findFragmentByTag(TAG_LIST_FRAGMENT);
        if (notesFragment == null)
            throw new IllegalStateException("ColorsListFragment not on screen");
        notesFragment.updateDataSet();
    }

}