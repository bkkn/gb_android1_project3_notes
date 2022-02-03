package me.bkkn.data.dummy;

import android.content.Context;

import com.snappydb.DB;
import com.snappydb.DBFactory;
import com.snappydb.SnappydbException;

import java.util.ArrayList;
import java.util.List;

import me.bkkn.domain.entity.Note;
import me.bkkn.domain.repository.Notes;

public class SnappyDBNotes implements Notes {

    private final Context context;

    public SnappyDBNotes(Context context) {
        this.context = context;
    }

    @Override
    public List<Note> getNotes() {
        final ArrayList<Note> list = new ArrayList<>();
        try {
            DB db = DBFactory.open(context);
            for (String[] batch : db.allKeysIterator().byBatch(10)) {
                for (String key : batch) {
                    list.add(db.getObject(key, Note.class));
                }
            }
            db.close();
        } catch (SnappydbException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public void deleteNote(Note note) {
        try {
            DB db = DBFactory.open(context);
            db.del(note.getIdString());
            db.close();
        } catch (SnappydbException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void changeTitle(Note note, String newTitle) {
        try {
            DB db = DBFactory.open(context);
            Note oldNote = db.getObject(note.getIdString(), Note.class);
            oldNote.setTitle(newTitle);
            db.put(note.getIdString(),oldNote);
            db.close();
        } catch (SnappydbException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void editNote(Note note, String newTitle, String newContent) {
        try {
            DB db = DBFactory.open(context);
            Note oldNote = db.getObject(note.getIdString(), Note.class);
            oldNote.setTitle(newTitle);
            oldNote.setContent(newContent);
            db.put(note.getIdString(), oldNote);
            db.close();
        } catch (SnappydbException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addNewNote() {
        int size = getNotes().size();
        addNewNote("title" + size, "content" + size);
    }

    @Override
    public void addNewNote(String title, String content) {
        int size = getNotes().size();
        Note note = new Note(size, title, content);
        try {
            DB db = DBFactory.open(context);
            db.put(note.getIdString(), note);
            db.close();
        } catch (SnappydbException e) {
            e.printStackTrace();
        }
    }
}
