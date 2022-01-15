package me.bkkn.ui;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import me.bkkn.App;
import me.bkkn.R;
import me.bkkn.domain.entity.Note;

public class NoteActivity extends AppCompatActivity {
    public static final String NOTE_EXTRA_KEY = "NOTE_EXTRA_KEY";
    private TextView titleTextView;
    private Button deleteButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        titleTextView = findViewById(R.id.name_text_view);
        Note note = getIntent().getParcelableExtra(NOTE_EXTRA_KEY);
        titleTextView.setText(note.getTitle());

        deleteButton = findViewById(R.id.delete_button);
        deleteButton.setOnClickListener(v -> {
            App.get(this).getNotes().deleteNote(note);
            setResult(RESULT_OK);
            finish();
        });
    }
}
