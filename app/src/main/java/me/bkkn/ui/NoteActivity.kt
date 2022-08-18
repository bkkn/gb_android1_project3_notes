package me.bkkn.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import me.bkkn.App;
import me.bkkn.R;
import me.bkkn.domain.entity.Note;

public class NoteActivity extends AppCompatActivity {
    public static final String NOTE_EXTRA_KEY = "NOTE_EXTRA_KEY";
    private EditText contentEditText;
    private TextView titleEditText;
    private Button deleteButton;
    private Button cancelButton;
    private Button okButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        titleEditText = findViewById(R.id.name_text_view);
        contentEditText = findViewById(R.id.content_text_view);
        Intent intent = getIntent();
        Note note = intent.getParcelableExtra(NOTE_EXTRA_KEY);

        titleEditText.setText(note.getTitle());
        contentEditText.setText(note.getText());

        deleteButton = findViewById(R.id.delete_button);
        deleteButton.setOnClickListener(v -> {
            App.get(this).getNotes().deleteNote(note);
            setResult(RESULT_OK);
            finish();
        });
        cancelButton = findViewById(R.id.cancel_button);
        cancelButton.setOnClickListener(v -> {
            setResult(RESULT_CANCELED);
            finish();
        });
        okButton = findViewById(R.id.ok_button);
        okButton.setOnClickListener(v -> {
            App.get(this).getNotes().editNote(note,
                    titleEditText.getText().toString(),
                    contentEditText.getText().toString());
            setResult(RESULT_OK);
            finish();
        });
    }
}
