package me.bkkn.ui;

import me.bkkn.domain.entity.Note;

interface OnNoteListener {
    void onDeleteNote(Note note);
    void onClickNote(Note note);
}