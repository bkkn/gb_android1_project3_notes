package me.bkkn;

import android.app.Application;
import android.content.Context;

import me.bkkn.data.dummy.DummyNotes;
import me.bkkn.domain.repository.Notes;

public class App extends Application {
    private static App sInstance = null;

    public final Notes notes = new DummyNotes();

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
    }

    public static App get() {
        return sInstance;
    }
}
