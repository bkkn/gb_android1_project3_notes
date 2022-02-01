package me.bkkn.ui.details;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.PopupMenu;
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

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_note_details, container, false);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        initPopUp(view);

        rootLayout = view.findViewById(R.id.note_details_root_linear_layout);
        noteTitleEditText = view.findViewById(R.id.name_edit_text);
        noteContentEditText = view.findViewById(R.id.content_edit_text);

        note = getArguments().getParcelable(NOTE_ARG_KEY);
        rootLayout.setBackgroundColor(note.getColor());
        noteTitleEditText.setText(note.getTitle());
        noteContentEditText.setText(note.getText());

        deleteButton = view.findViewById(R.id.delete_button);
        deleteButton.setOnClickListener(v -> {
            App.get().notes.deleteNote(note);
            controller.popBackFragment();
            controller.updateDataSet();
        });
        cancelButton = view.findViewById(R.id.cancel_button);
        cancelButton.setOnClickListener(v -> {
            controller.popBackFragment();
        });
        okButton = view.findViewById(R.id.ok_button);
        okButton.setOnClickListener(v -> {
            App.get().notes.editNote(note,
                    noteTitleEditText.getText().toString(),
                    noteContentEditText.getText().toString());
            controller.popBackFragment();
            controller.updateDataSet();
        });
    }

    private void initPopUp(View view) {

        view.setOnLongClickListener(v -> {

            Activity activity = requireActivity();
            PopupMenu popupMenu = new PopupMenu(activity, v);
            activity.getMenuInflater().inflate(R.menu.popup, popupMenu.getMenu());

            EditText titleEditText = view.findViewById(R.id.name_edit_text);
            EditText contentEditText = view.findViewById(R.id.content_edit_text);


            popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.action_popup_clear:
                            titleEditText.setText("");
                            contentEditText.setText("");
                            return true;
                    }
                    return true;
                }
            });
            popupMenu.show();
            return true;
        });
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.fragment_note_details_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.app_bar_save_note:
                App.get().notes.editNote(note,
                        noteTitleEditText.getText().toString(),
                        noteContentEditText.getText().toString());
                controller.updateDataSet();
                Toast.makeText(getContext(),
                        getResources().getText(R.string.on_save_toast_message),
                        Toast.LENGTH_SHORT).show();
                return true;
            case R.id.app_bar_delete_note:
                App.get().notes.deleteNote(note);
                controller.updateDataSet();
                controller.popBackFragment();
                Toast.makeText(getContext(),
                        getResources().getText(R.string.on_delete_toast_message),
                        Toast.LENGTH_SHORT).show();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public interface Controller {

        void popBackFragment();

        void updateDataSet();
    }
}
