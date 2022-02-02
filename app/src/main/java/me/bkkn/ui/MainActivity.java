package me.bkkn.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuItemWrapperICS;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.android.material.snackbar.Snackbar;

import me.bkkn.App;
import me.bkkn.R;
import me.bkkn.domain.entity.Note;
import me.bkkn.ui.details.NoteDetailsFragment;
import me.bkkn.ui.dialog.AlertDialogFragment;
import me.bkkn.ui.list.NotesFragment;

public class MainActivity
        extends AppCompatActivity
        implements NotesFragment.Controller, NoteDetailsFragment.Controller, AlertDialogFragment.Controller {

    public static final String ALERT_DIALOG_TAG = "alert_dialog_tag";
    private static final String TAG_LIST_FRAGMENT = "TAG_LIST_FRAGMENT";
    private static final String TAG = "@@@";
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
        new AlertDialogFragment()
                .show(getSupportFragmentManager(), ALERT_DIALOG_TAG);
    }

    @Override
    public void popBackFragment() {
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void updateDataSet() {
        NotesFragment notesFragment = (NotesFragment) getSupportFragmentManager().findFragmentByTag(TAG_LIST_FRAGMENT);
        if (notesFragment == null)
            throw new IllegalStateException(getString(R.string.ColorsListFragment_not_on_screen));
        notesFragment.updateDataSet();
        notesFragment.scrollToAdded();
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
            if (++pressedCount % 2 == 0)
                super.onBackPressed();
            else {
                View view = findViewById(R.id.activity_main__main_fragment_container);
                Snackbar
                        .make(this, view, getString(R.string.backpressed_message), Snackbar.LENGTH_SHORT)
                        .setAction(R.string.snackbar_button_label, v -> super.onBackPressed())
                        .setActionTextColor(ContextCompat.getColor(this, android.R.color.holo_red_light))
                        .show();
            }
        } else {
            pressedCount = 0;
            super.onBackPressed();
        }
    }

    @Override
    public void addNewNote(String title, String content) {
        App.get().notes.addNewNote(title, content);
        updateDataSet();
    }
}