package me.bkkn.domain.repository;

import java.util.List;

import me.bkkn.domain.entity.Note;

public interface Notes {
    List<Note>getNotes();

    void deleteNote(Note note);

    void addNewNote();
}
