package me.bkkn;

import android.app.Application;
import android.content.Context;

import me.bkkn.data.dummy.DummyNotes;
import me.bkkn.data.dummy.SharedPreferencesNotes;
import me.bkkn.domain.repository.Notes;

public class App extends Application {
    private static App sInstance = null;

    public Notes notes;

    @Override
    public void onCreate() {
        super.onCreate();
        notes = new SharedPreferencesNotes(this);
        sInstance = this;
    }

    public static App get() {
        return sInstance;
    }
}
