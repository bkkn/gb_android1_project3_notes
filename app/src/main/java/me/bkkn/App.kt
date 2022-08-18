package me.bkkn;

import android.app.Application;
import android.content.Context;

import me.bkkn.data.dummy.DummyNotes;
import me.bkkn.domain.repository.Notes;

public class App extends Application {
    private Notes notes = new DummyNotes();

    public static App get(Context context) {
        return (App) context.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public Notes getNotes() {
        return notes;
    }
}
