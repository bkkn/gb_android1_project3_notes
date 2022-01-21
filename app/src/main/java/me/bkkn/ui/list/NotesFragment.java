package me.bkkn.ui.list;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import me.bkkn.App;
import me.bkkn.R;
import me.bkkn.domain.entity.Note;
import me.bkkn.domain.repository.Notes;

public class NotesFragment extends Fragment {
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
            notes.addNewNote();
            List<Note> list = notes.getNotes();
            adapter.setData(list);
            adapter.notifyItemInserted(list.size() - 1);
            recyclerView.smoothScrollToPosition(list.size() - 1);
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

    public void updateDataSet() {
        adapter.setData(notes.getNotes());
    }

    public interface Controller {
        void showNoteDetails(Note note);
    }
}
