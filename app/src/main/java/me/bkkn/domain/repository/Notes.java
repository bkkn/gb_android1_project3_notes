package me.bkkn.domain.repository;

import java.util.List;

import me.bkkn.domain.entity.Note;

public interface Notes {
    List<Note>getNotes();

    void deleteNote(Note note);

    void changeTitle(Note note, String newTitle);

    void editNote(Note note, String newTitle, String newContent);

    void addNewNote();

    void addNewNote(String title, String content);

    default int index(Note note)
    {
        return getNotes().indexOf(note);
    }
}
