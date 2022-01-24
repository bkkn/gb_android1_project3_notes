package me.bkkn.ui;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.snackbar.Snackbar;

import me.bkkn.R;
import me.bkkn.domain.entity.Note;
import me.bkkn.ui.details.NoteDetailsFragment;
import me.bkkn.ui.list.NotesFragment;

public class MainActivity
        extends AppCompatActivity
        implements NotesFragment.Controller, NoteDetailsFragment.Controller {

    private static final String TAG_LIST_FRAGMENT = "TAG_LIST_FRAGMENT";
    private int pressedCount = 0;

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
    public void showNewNoteDialog() {
        Note note = new Note();
        AlertDialogFragment alertDialogFragment = AlertDialogFragment.newInstance(note);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.activity_main__second_fragment_container, alertDialogFragment)
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

    @Override
    public void onBackPressed() {

        if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
            if (++pressedCount % 2 == 0)
                super.onBackPressed();
            else {
                View view = findViewById(R.id.activity_main__main_fragment_container);
                Snackbar
                        .make(this, view, "One more 'back', or --->", Snackbar.LENGTH_SHORT)
                        .setAction("click me to exit", v -> {
                            super.onBackPressed();
                        })
                        .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                        .show();
            }
        } else {
            pressedCount = 0;
            super.onBackPressed();
        }
    }
}