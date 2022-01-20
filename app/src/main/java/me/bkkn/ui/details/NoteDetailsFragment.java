package me.bkkn.ui.details;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import me.bkkn.App;
import me.bkkn.R;
import me.bkkn.domain.entity.Note;

public class NoteDetailsFragment extends Fragment {

    private static final String NOTE_ARG_KEY = "NOTE_ARG_KEY";

    private Controller controller;
    private LinearLayout rootLayout;
    private EditText noteTitleEditText;
    private EditText noteContentEditText;
    private Note note;
    private Button cancelButton;
    private Button deleteButton;
    private Button okButton;

    public static NoteDetailsFragment newInstance(Note note) {
        NoteDetailsFragment fragment = new NoteDetailsFragment();

        Bundle bundle = new Bundle();
        bundle.putParcelable(NOTE_ARG_KEY, note);

        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof Controller) {
            controller = (Controller) context;
        } else {
            throw new IllegalStateException("Activity must implement NoteDetailsFragment.Controller");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_note_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        rootLayout = view.findViewById(R.id.note_details_root_linear_layout);
        noteTitleEditText = view.findViewById(R.id.name_edit_text);
        noteContentEditText = view.findViewById(R.id.content_edit_text);

        note = getArguments().getParcelable(NOTE_ARG_KEY);
        rootLayout.setBackgroundColor(note.getColor());
        noteTitleEditText.setText(note.getTitle());
        noteContentEditText.setText(note.getText());

        deleteButton = view.findViewById(R.id.delete_button);
        deleteButton.setOnClickListener(v -> {
            App.get().notes.deleteNote(note); // TODO delete by id
            controller.onDeleteNote(note.getId());
        });
        cancelButton = view.findViewById(R.id.cancel_button);
        cancelButton.setOnClickListener(v -> {
            controller.onCloseNote(note.getId());
        });
        okButton = view.findViewById(R.id.ok_button);
        okButton.setOnClickListener(v -> {
            App.get().notes.editNote(note,
                    noteTitleEditText.getText().toString(),
                    noteContentEditText.getText().toString()); // TODO delete by id
            controller.onSaveNote(note.getId(),
                    noteTitleEditText.getText().toString(),
                    noteContentEditText.getText().toString());
        });
    }

    public interface Controller {
        void onDeleteNote(int noteId);

        void onCloseNote(int noteId);

        void onSaveNote(int noteId, String newTitle, String newContent);
    }
}
