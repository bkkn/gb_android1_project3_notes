package me.bkkn.ui.dialog;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import me.bkkn.R;

public class AlertDialogFragment extends DialogFragment {
    EditText titleEditText;
    EditText contentEditText;
    Button okButton;
    Button cancelButton;
    Controller controller;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof AlertDialogFragment.Controller) {
            controller = (Controller) context;
        } else {
            throw new IllegalStateException("Activity must implement AlertDialogFragment.Controller");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_new_note, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        titleEditText = view.findViewById(R.id.dialog__note_title);
        contentEditText = view.findViewById(R.id.dialog__note_content);
        okButton = view.findViewById(R.id.dialog_ok_button);
        cancelButton = view.findViewById(R.id.dialog_cancel_button);

        okButton.setOnClickListener(v -> {
            controller.addNewNote(titleEditText.getText().toString(),
                    contentEditText.getText().toString());
            this.dismiss();
        });
        cancelButton.setOnClickListener(view1 -> dismiss());
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setCancelable(false);
    }

    public interface Controller {
        void addNewNote(String title, String content);
    }
}
