package me.bkkn.data.dummy;

import java.util.ArrayList;
import java.util.List;

import me.bkkn.domain.entity.Note;
import me.bkkn.domain.repository.Notes;

public class DummyNotes implements Notes {
    List<Note> list = new ArrayList<>();

    public DummyNotes() {
        for (int i = 0; i < 10; i++) {
            list.add(new Note("title" + i, "text" + i, 0));
        }
    }

    @Override
    public List<Note> getNotes() {
        return null;
    }

    @Override
    public void deleteNote(Note note) {

    }
}
