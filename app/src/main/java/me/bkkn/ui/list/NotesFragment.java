package me.bkkn.ui.list;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import me.bkkn.App;
import me.bkkn.R;
import me.bkkn.domain.entity.Note;
import me.bkkn.domain.repository.Notes;

public class NotesFragment extends Fragment {
    public static final String TAG = "@@@";
    private Notes notes;

    private RecyclerView recyclerView;
    private NoteAdapter adapter;
    private Controller controller;
    private Button addNewNoteButton;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof Controller) {
            controller = (Controller) context;
        } else {
            throw new IllegalStateException("Activity must implement NotesFragment.Controller");
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_notes, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        notes = App.get().notes;

        addNewNoteButton = view.findViewById(R.id.add_note_button);

        initRecycler(view);
    }

    private void initRecycler(View view) {
        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        addNewNoteButton.setOnClickListener(v -> {
            controller.showNewNoteDialog();
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

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
                controller.showNoteDetails(note);
            }
        });

        recyclerView.setAdapter(adapter);
    }

    private void addNewNote() {
        notes.addNewNote();
        List<Note> list = notes.getNotes();
        adapter.setData(list);
        adapter.notifyItemInserted(list.size() - 1);
        recyclerView.smoothScrollToPosition(list.size() - 1);
    }

    public void updateDataSet() {
        adapter.setData(notes.getNotes());
    }

    public void filterDataSet(String query) {
        //todo implement search
        Toast.makeText(getContext(), "search", Toast.LENGTH_SHORT).show();
    }

    public void scrollToAdded() {
        List<Note> list = notes.getNotes();
        recyclerView.smoothScrollToPosition(list.size() - 1);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.fragment_notes_menu, menu);
        final MenuItem searchItem = menu.findItem(R.id.app_bar_search);
        final SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                adapter.filter(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.filter(newText);
                return true;
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Log.d(TAG, "onOptionsItemSelected() called with: item = [" + item + "]");
        //Toast.makeText(this, "Activity: " + item.getTitle(), Toast.LENGTH_SHORT).show();
        switch (item.getItemId()) {
            case R.id.app_bar_add_note:
                controller.showNewNoteDialog();
                Toast.makeText(getContext(), item.getTitle(), Toast.LENGTH_SHORT).show();
                break;

            case R.id.app_bar_search:
                Toast.makeText(getContext(), item.getTitle(), Toast.LENGTH_SHORT).show();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public interface Controller {
        void showNoteDetails(Note note);

        void showNewNoteDialog();
    }
}
