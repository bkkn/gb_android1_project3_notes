package me.bkkn.data.dummy;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import me.bkkn.domain.entity.Note;
import me.bkkn.domain.repository.Notes;

public class SharedPreferencesNotes implements Notes {

    public static final String SHARED_PREFS_NAME = "SHARED_PREFS_NAME";
    public static final String SHARED_PREFS_NOTES_KEY = "SHARED_PREFS_NOTES_KEY";
    private final Gson gson = new Gson();
    private SharedPreferences sharedPreferences;

    public SharedPreferencesNotes(Context context) {
        this.sharedPreferences = context.getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE);
    }

    private void saveToSharedPrefs(List<Note> notes) {
        final String jsonString = gson.toJson(notes);
        sharedPreferences
                .edit()
                .putString(SHARED_PREFS_NOTES_KEY, jsonString)
                .apply();
    }

    @Override
    public void addNewNote() {
        final List<Note> notes = getNotes();
        notes.add(0, new Note(notes.size(), "title", "content"));
        saveToSharedPrefs(notes);
    }

    @Override
    public void addNewNote(String title, String content) {
        final List<Note> notes = getNotes();
        notes.add(new Note(notes.size(), title, content));
        saveToSharedPrefs(notes);
    }

    @Override
    public List<Note> getNotes() {
        final String notesJson = sharedPreferences.getString(SHARED_PREFS_NOTES_KEY, "");
        if (!TextUtils.isEmpty(notesJson)) {
            Type type = new TypeToken<ArrayList<Note>>() {
            }.getType();
            return gson.fromJson(notesJson, type);
        } else {
            return new ArrayList<>();
        }
    }

    @Override
    public void deleteNote(Note note) {
        final List<Note> notes = getNotes();
        notes.remove(note);
        saveToSharedPrefs(notes);
    }

    @Override
    public void changeTitle(Note note, String newTitle) {
        final List<Note> notes = getNotes();
        int index = notes.indexOf(note);
        notes.get(index).setTitle(newTitle);
        saveToSharedPrefs(notes);
    }

    @Override
    public void editNote(Note note, String newTitle, String newContent) {
        final List<Note> notes = getNotes();
        int index = notes.indexOf(note);
        notes.get(index).setContent(newContent);
        saveToSharedPrefs(notes);
    }
}
