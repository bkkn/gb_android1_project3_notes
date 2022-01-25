package me.bkkn.data.dummy;

import java.util.ArrayList;
import java.util.List;

import me.bkkn.domain.entity.Note;
import me.bkkn.domain.repository.Notes;

public class DummyNotes implements Notes {
    List<Note> list = new ArrayList<>();

    public DummyNotes() {
        for (int i = 0; i < 10; i++) {
            list.add(new Note(i, "title" + i, "text" + i));
        }
    }

    @Override
    public List<Note> getNotes() {
        return list;
    }

    @Override
    public void deleteNote(Note note) {
        list.remove(note);
    }

    @Override
    public void changeTitle(Note note, String newTitle) {
        int index = list.indexOf(note);
        list.get(index).setTitle(newTitle);
    }

    @Override
    public void editNote(Note note, String newTitle, String newContent) {
        int index = list.indexOf(note);
        list.get(index).setTitle(newTitle);
        list.get(index).setText(newContent);
    }

    @Override
    public void addNewNote() {
        int i = list.size();
        list.add(new Note(i, "new" + i, "new" + i));
    }

    @Override
    public void addNewNote(String title, String content) {
        int i = list.size();
        list.add(new Note(i, title, content));
    }
}
