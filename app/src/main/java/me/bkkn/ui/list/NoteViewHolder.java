package me.bkkn.ui.list;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import me.bkkn.R;
import me.bkkn.domain.entity.Note;

public class NoteViewHolder extends RecyclerView.ViewHolder {
    private final TextView nameTextView = itemView.findViewById(R.id.title_text_view);
    private final TextView contentTextView = itemView.findViewById(R.id.content_edit_text);
    private final ImageButton deleteButton = itemView.findViewById(R.id.delete_button);

    private OnNoteListener onNoteListener;

    public NoteViewHolder(
            @NonNull LayoutInflater inflater,
            @NonNull ViewGroup parent,
            OnNoteListener onNoteListener
    ) {
        super(inflater.inflate(R.layout.item_note, parent, false));
        this.onNoteListener = onNoteListener;
    }

    public void bind(Note note) {
        deleteButton.setOnClickListener(v -> onNoteListener.onDeleteNote(note));
        itemView.setOnClickListener(v -> onNoteListener.onClickNote(note));

        nameTextView.setText(note.getTitle());
        contentTextView.setText(note.getText());
    }

    public interface OnNoteListener {
        void onDeleteNote(Note note);
        void onClickNote(Note note);
    }
}